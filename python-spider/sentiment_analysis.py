import json
import os

from snownlp import sentiment
import pandas as pd
import snownlp
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties
#from word_cloud import word_cloud_creation, word_cloud_implementation, word_cloud_settings

"""
    Written by 77fox
    Date: 2020/03/10
"""

# 词云字体
#WC_FONT_PATH = '/Library/Fonts/Songti.ttc'  #用于Mac OS
WC_FONT_PATH = 'C://WINDOWS/FONTS/MSYHL.TTC'    #用于Windows

# 编码方式
ENCODING = 'gbk'    #Mac OS换成'utf-8'

def read_csv():
    '''读取商品评论数据文件'''
    comment_data = pd.read_csv('jd_comment.csv', encoding=ENCODING,
                               sep='\n', index_col=None)
    return comment_data


def clean_data(data):
    '''数据清洗'''
    df = data.dropna()  # 消除缺失数据 NaN为缺失数据
    df = pd.DataFrame(df.iloc[:, 0].unique())  # 数据去重
    return df
    # print('数据清洗后：', len(df))


def clean_repeat_word(raw_str, reverse=False):
    '''去除评论中的重复使用的词汇'''
    if reverse:
        raw_str = raw_str[::-1]
    res_str = ''
    for i in raw_str:
        if i not in res_str:
            res_str += i
    if reverse:
        res_str = res_str[::-1]
    return res_str


def processed_data(filename):
    '''清洗完毕的数据，并保存'''
    df = clean_data(read_csv())
    ser1 = df.iloc[:, 0].apply(clean_repeat_word)
    df2 = pd.DataFrame(ser1.apply(clean_repeat_word, reverse=True))
    df2.to_csv(f'{filename}.csv', encoding=ENCODING, index_label=None, index=None)


def train():
    '''训练正向和负向情感数据集，并保存训练模型'''
    print('正在训练正向和负向情感数据集...')
    sentiment.train('neg.txt', 'pos.txt')
    sentiment.save('sentiment.marshal')


sentiment_list = []

res_list = []


def test(filename, to_filename):
    '''商品评论-情感分析'''
    averageSentiment = 0

    with open(f'{filename}.csv', 'r', encoding=ENCODING) as fr:
        
        for line in fr.readlines():
            s = snownlp.SnowNLP(line)
            averageSentiment += s.sentiments
            if s.sentiments > 0.6:
                res = '喜欢'
                res_list.append(1)
            elif s.sentiments < 0.4:
                res = '不喜欢'
                res_list.append(-1)
            else:
                res = '一般'
                res_list.append(0)
            sent_dict = {
                '情感分析结果': s.sentiments,
                '评价倾向': res,
                '商品评论': line.replace('\n', '')
            }
            sentiment_list.append(sent_dict)
            #print(sent_dict)

        # 计算情感分析值的平均数
        averageSentiment = averageSentiment / len(sentiment_list)
        #print('Average sentiment value: ' + averageSentiment)
        # 转为JSON对象
        result = {'comments_num': len(sentiment_list), 'average_sentiment': averageSentiment }
        json_result = json.dumps(result, sort_keys=True, indent=4, separators=(',', ':'))
        print(json_result)

        # 将结果写入文件
        if os.path.exists('average_sentiment.txt'):
            os.remove('average_sentiment.txt')
        with open(f'average_sentiment.txt','x', encoding='utf8') as text_file:
            text_file.write(json_result + '\n')


        df = pd.DataFrame(sentiment_list)
        df.to_csv(f'{to_filename}.csv', index=None, encoding=ENCODING,
                  index_label=None, mode='w')


def data_virtualization():
    '''分析结果可视化，以条形图为测试样例'''
    font = FontProperties(fname=WC_FONT_PATH, size=14)
    likes = len([i for i in res_list if i == 1])
    common = len([i for i in res_list if i == 0])
    unlikes = len([i for i in res_list if i == -1])

    plt.bar([1], [likes], label='like')
    plt.bar([3], [common], label='common')
    plt.bar([5], [unlikes], label='unlike')

    plt.legend()
    plt.xlabel('result')
    plt.ylabel('value')
    plt.title(u'商品评论情感分析结果-条形图', FontProperties=font)
    plt.savefig('fig.png')
    #plt.show()

'''
def word_cloud_show():
    #将商品评论转为高频词汇的词云
    wl = word_cloud_creation('jd_comment.csv')
    wc = word_cloud_settings()
    word_cloud_implementation(wl, wc)
'''

def main():
    processed_data('processed_comment_data')

    # 训练正负向商品评论数据集
    #train()  

    # 情感分析
    print('\n正在评分...\n')
    test('jd_comment', 'result')

    print('\n数据可视化\n')
    data_virtualization()  # 数据可视化

    #print('END OF Python')


if __name__ == '__main__':
    main()



