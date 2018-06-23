# -*- coding: UTF-8 -*-

import matplotlib.pyplot as plt


def safe_divide(a, b):
    if a == 0:
        return 0.0
    elif b == 0:
        return 0.0
    else:
        return a / b


def calculate_metrics(predictions, threshold):
    true_positive = 0
    false_positive = 0
    true_negative = 0
    false_negative = 0
    for (url_id, mark, url, prediction) in predictions:
        mark_predict = prediction > threshold

        if mark_predict:
            if mark_predict == mark:
                true_positive += 1
            else:
                false_positive += 1
        else:
            if mark_predict == mark:
                true_negative += 1
            else:
                false_negative += 1

    class_prec = safe_divide(true_positive, true_positive + false_positive)
    class_recall = safe_divide(true_positive, true_positive + false_negative)

    class_F1 = safe_divide(2 * class_prec * class_recall, class_prec + class_recall)

    not_class_prec = safe_divide(true_negative, true_negative + false_negative)
    not_class_recall = safe_divide(true_negative, true_negative + false_positive)

    not_class_F1 = safe_divide(2 * not_class_prec * not_class_recall, not_class_prec + not_class_recall)

    return ((class_prec, class_recall, class_F1), (not_class_prec, not_class_recall, not_class_F1))


def arange(start, stop, step):
    cur_value = start
    while True:
        if cur_value > stop: break
        yield cur_value
        cur_value += step


def plot_results(classifier, docs, min_threshold=-1, max_threshold=1, step=0.1, trace=False):
    x = []
    y_p = []
    y_n = []
    docs_predictions = classifier.predict_all(docs)
    for threshold in arange(min_threshold, max_threshold, step):
        r = calculate_metrics(docs_predictions, threshold)
        x.append(threshold)
        y_p.append(r[0])
        y_n.append(r[1])
        if trace:
            print 'threshold %s' % threshold
            print '\tclass_prec %s, class_recall %s, class_F1 %s' % r[0]
            print '\tnot_class_prec %s, not_class_recall %s, not_class_F1 %s' % r[1]
            print '\t\tMacroF1Mesure %s' % ((r[0][2] + r[1][2]) / 2)
    plot_stats(x, y_p, "Class Result")
    plot_stats(x, y_n, "Not class Result")


def plot_stats(x, y, title):
    plt.figure(figsize=(10, 5))

    prec, = plt.plot(x,
                     [k[0] for k in y], "r", label='Precision',
                     linewidth=1)
    accur, = plt.plot(x,
                      [k[1] for k in y], "b", label='Recall',
                      linewidth=1)
    f1, = plt.plot(x,
                   [k[2] for k in y], "g", label='F1',
                   linewidth=1)
    plt.grid(True)
    plt.legend(handles=[prec, accur, f1])
    plt.title(title)
    plt.show()


def plot_feature(docs, feature_num, title='Title'):
    is_spam_data = [doc.features.feature_vector[feature_num] for doc in docs if doc[1] == True]
    not_spam_data = [doc.features.feature_vector[feature_num] for doc in docs if doc[1] == False]
    bins = 100
    plt.hist(is_spam_data, bins=bins, color='red', normed=True, alpha=0.7, label='spam')
    plt.hist(not_spam_data, bins=bins, color='blue', normed=True, alpha=0.7, label='not_spam')
    plt.title(title)
    plt.legend()
    plt.show()


if __name__ == '__main__':
    pass
