# coding:utf-8
'''
    测试Xpath的脚本
    author: cw
'''
import requests 
import re

# requests请求网页，得到网页源代码 
url = 'https://item.jd.com/100000287133.html' 
r = requests.get(url).content

# 导入lxml库和html.fromStringh函数来解析html 
from lxml import html 

# 调用html.fromString函数解析html源代码 
sel = html.fromstring(r)

# 获取完整名称
fullName = sel.xpath("//div[@class='sku-name']/text()")
if(fullName[0] is not None):
    matchObj = re.match(r'\s*(.+)\s*', fullName[0])     #截取非空白字符
    if(matchObj):
        fullName = matchObj.group(1)
else:
    fullName = ''
print(fullName)

# 提取商家信息，text()获取该标签下的文本 
shop = sel.xpath("//*[@id='crumb-wrap']/div/div[2]/div[2]/div[1]/div/a/text()")[0]
if(shop is None):
    shop = ''
print(shop)
    
