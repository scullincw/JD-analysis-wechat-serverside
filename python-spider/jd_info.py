import sys
import os
import requests
import re
import urllib
def getHTMLText(url):
    try:
        r = requests.get(url, timeout=30)
        r.raise_for_status()  #如果状态不是200 引发http error异常
        r.encoding = r.apparent_encoding
        return r.text
    except:
        #todo: 改为抛出异常对象？
        return "产生异常"

def getName(html):
    title = re.findall('<title>(.*?)</title>', html)[-1]
    matchObj = re.match( r'(.*)(【(.*)】(.*))', title)
    if matchObj:
        title = matchObj.group(1)
    #print(title)

    # 将结果写入文件
    if os.path.exists('jd_info.txt'):
        os.remove('jd_info.txt')
    with open(f'jd_info.txt','x', encoding='utf8') as text_file:
        text_file.write(title + '\n')

def getPicture(html):
    #urls = re.findall('<li ><img alt=(.*?) data-url', html)
    reg = r'src="\/\/(.+?\.jpg)"'
    imgre = re.compile(reg)
    imglist = re.findall(imgre, html)
    #print(imglist)
    x = 0
    for imgurl in imglist:
        print (imgurl)
        #urllib.request.urlretrieve('https://'+imgurl, '%s.jpg' % x)
        x += 1
    return imglist


if __name__ == "__main__":
    url = sys.argv[1]      #"https://item.jd.com/100009879562.html"
    html=getHTMLText(url)
    getName(html)
    #getPicture(html)
    #print(getHTMLText(url))
#html = response.read().decode('utf-8')   #调用read()进行读取，转换为utf-8的编码

#html1 = urllib.request.urlopen(url).read()
#html1 = str(html1)
#print(html)  #打印



#解析网页
#dir_name = re.findall('<title>(.*?)</title>', html)[-1]
#print(dir_name)
#if not os.path.exists(dir_name):
 #   os.mkdir(dir_name)

#urls = re.findall('<img src="(.*?)" class="focus_img">', html)
#print(urls)
#print(urls)

#保存图片
#for url in urls:
 #   time.sleep(1)
  #  file_name = url.split('/')[-1]
   # response = requests.get(url, headers=headers)
    #with open(dir_name +'/' + file_name, 'wb') as f:
     #   f.write(response.content)
