# -*- coding: utf-8 -*-
"""
Created on Sat May  2 00:57:32 2015

@author: Romain
"""

import numpy as np
from sklearn.utils import check_random_state
from sklearn.cross_validation import train_test_split
from sklearn.cross_validation import LeavePLabelOut
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.ensemble import RandomForestClassifier
from sklearn.grid_search import GridSearchCV
import matplotlib.pyplot as plt
from sklearn.metrics import make_scorer
from sklearn.metrics import pairwise_distances


def load_corpus(filepath, corpus_number):
    with open(filepath, 'r', encoding="utf-8") as f:
        txt_data = f.read()
        lines = np.array(txt_data.split("\n"))
        corpus = [line.split("\t") for line in lines]
        out_corpus = []
        # concat the file rows and convert numeric types
        for i in range(0,len(corpus)):
            row = corpus[i]
            
            if(len(row) == 4):
                text = row[1] + " " + row[2]
                out_corpus.append([corpus_number, text, row[0], row[3]])
                
    return np.array(out_corpus)
    
def dist(y_p, y):
    dist_ = pairwise_distances(y_p, y, metric='manhattan')
    return 4 - (dist_[0,0] / len(y_p))


def load_all_corpus(files, max_entries=2000):
    corpus_counter = 0   
    corpora = []
    
    for file in files:
        print("load {}...".format(file))
        tmp_corpus = load_corpus(file, corpus_counter)
        
        # remove entries from the corpus there are too many
        if tmp_corpus.shape[0] > max_entries :
            indexes = np.arange(tmp_corpus.shape[0])
            np.random.shuffle(indexes)
            tmp_corpus = tmp_corpus[indexes[:max_entries],:]
            
        if len(corpora) == 0 :
            corpora = tmp_corpus
        else:
            corpora = np.append(corpora, tmp_corpus, axis=0)
        
        corpus_counter += 1
        
    labels = corpora[:,0].astype(int)
    texts = corpora[:,1]
    stars = corpora[:,2].astype(int)
    scores = corpora[:,3].astype(float)    
    
    return texts, stars, scores, labels
    
    
if __name__ == "__main__":
    
    files = ["B0000B35EQ_corpus.txt", "B0009F3RVU_corpus.txt", 
             "B0009IBJAS_corpus.txt", "B000E81VN8_corpus.txt", 
             "B000FCRS0I_corpus.txt", "B000FKBCX4_corpus.txt", 
             "B000HCZ9BG_corpus.txt",
             "B000WJX4BQ_corpus.txt", "B001CCHUTE_corpus.txt",
             "B002YCRHU8_corpus.txt", "B002ZBWKAU_corpus.txt",
             "B003IHUHGE_corpus.txt", "B004KPKS26_corpus.txt"]
             
    texts, stars, scores, labels = load_all_corpus(files, max_entries=1000) 
    
    print("Compute the TF-IDF matrix (with min_df = 0.001)...")
    vectorizer = TfidfVectorizer(min_df=0.001).fit(texts)
    tfidf = vectorizer.transform(texts)
    
    # merge opinion score to tdidf
    labels = np.reshape(labels, (len(labels), 1))
    scores = np.reshape(scores, (len(scores), 1))
    tf_opin = np.append(scores, tfidf.toarray(), axis=1)
    tf_opin_labels = np.append(labels, tf_opin, axis=1)
    
    # ML
    print("Splitting training set into validation sets...")
    random_state = check_random_state(0)
    
    X_train, X_test, y_train, y_test = train_test_split(tf_opin_labels, 
                                                        stars, test_size=0.25, 
                                                        random_state=random_state)
    label_train = X_train[:,0]
    X_train = X_train[:,1::]
    label_test = X_test[:,0]
    X_test = X_test[:,1::]
    
    print("Fit the classifier (without opinion data)...")
    clf1 = RandomForestClassifier(n_estimators=100, n_jobs=3)
    clf1.fit(X_train[:,1::], y_train)
    print("Compute the score...")
    print("Score : {}".format(clf1.score(X_test[:,1::], y_test)))
    
    print("Select features that seems the most relevant...")
    imp = clf1.feature_importances_
    ind = imp > (np.mean(imp) + 0.6 * np.std(imp))
    X_train = np.append(X_train[:,0:1],  X_train[:,1::][:,ind], axis=1)
    X_test = np.append(X_test[:,0:1],  X_test[:,1::][:,ind], axis=1)
    
    print("Prepare the parameter tuning...")
    rf_clf = RandomForestClassifier(n_estimators=50, 
                                    random_state=random_state,
                                    criterion='entropy',
                                    n_jobs=3)
                                    
    param_grid = {'max_depth': [18,22,24],
                  'max_features': [1.0, 0.9, 0.85, 0.75]}                           

    print("Tune the parameters...")   
    # use cosine similarity
    scr = make_scorer(dist)
    grid = GridSearchCV(rf_clf, 
                        param_grid,
                        cv=LeavePLabelOut(label_train, p=1),
                        verbose=10,
                        scoring=scr).fit(X_train, y_train)
                  
    print("Select the best parameters and retrain with more estimators...")
    b_clf = grid.best_estimator_.set_params(n_estimators=2000)
                                           
    print("Best parameters...")
    print(grid.best_params_)
    print("Train the classifier...")
    b_clf.fit(X_train, y_train)
    print("Re-Compute the score...")
    print("Accuracy : {}".format(b_clf.score(X_test, y_test)))
    distance = 4 - dist(b_clf.predict(X_test), y_test)
    print("Average distance : {}".format(distance))
