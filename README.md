# lawa-法律中文分词ES插件  
  lawa-plugin是Elasticsearch的中文分词插件，后端模型是由法律法规、案例、期刊语料统计而成，并具有新词发现功能。


特点
====
-  从法规、案例、期刊语料库经过IK插件（MAX模式）分词后得到法律全库的词、词频文件。  
-  经过IK分词后，法律库共有19.34W个词，经过与IK词典比较后发现，有3.0W个词未在IK词典中出现，分析这些词发现：有一部分为单子词，约1.2W个；另一部分为数量词，约有0.85W个。  
-  对于IK中缺失的单字词，由于其语义具有独立含义，如丑、转、丕、世等，因此在新词词表中进行保留。
   而对于IK使用规则方式分拆出的数量词，如廿九中、拾万元、拾亩等词，在语句中的语义表示中，利用新词发现功能可以正确拆分，因此去掉这部分词表。
-  构建的新词表为全库中，部分IK中的词表17.28W，单字词1.2W，共计18.47W作为新词表，用于分词模型。


安装说明  
========
-  Elasticsearch插件安装  
插件安装命令
```bin\elasticsearch-plugin install file:///《path to lawa-plugin-1.0.0.zip>```

-  Elasticsearch权限修改  
在es安装目录下的config目录中的jvm.options中增加一行：
```
-Djava.security.policy=<path to `java.policy`>
```
java.policy文件放置于es安装目录的plugins\lawa-plugin\configs文件夹下。

-  分词模型地址修改  
修改plugins\lawa-plugin\configs\core.config文件中modelserver指定的模型服务，如下：
```
modelserver=http://192.168.60.15:58086/z
```

-  模型服务启动
* 模型服务使用了falcon、waitress框架，分词模型使用了lawa. 使用pip即可安装```pip install lawa falcon falcon_cors waitress```.
    * torch_server.py: https://github.com/ShenDezhou/lawa/blob/master/torch_server.py

```
#前台启动则使用：
python torch_server.py
#需要放后台则使用：
nohup python torch_server.py &
```


主要功能
=======
新词发现功能
---------------------  
示例1：
【新词识别】：中华人民共和国/ 民法典    (此处，“民法典”并没有在词典中，但是也被Viterbi算法识别出来了)
【新词识别】：全国人民代表大会/ 常务委员会/ 关于/ 授予/ 在/ 抗击/ 新冠/ 肺炎/ 疫情/ 斗争/ 中/ 作出/ 杰出贡献/ 的/ 人士/ 国家/ 勋章/ 和/ 国家/ 荣誉称号/ 的/ 决定    (此处，“新冠”并没有在词典中，但是也被Viterbi算法识别出来了)

分词特点
---------------------  
默认启动了MAX模式，即给出文本的所有可能拆分，以扩大召回。
示例2：
在中华人民共和国境内缴纳增值税、消费税的单位和个人，为城市维护建设税的纳税人，应当依照本法规定缴纳城市维护建设税。
【搜索引擎模式】： 在, 中华, 华人, 人民, 共和, 共和国, 中华人民共和国, 境内, 缴纳, 增值, 增值税, 、, 消费, 消费税, 的, 单位, 和, 个人, ，, 为, 城市, 维护, 建设, 税, 的, 纳税, 纳税人, ，, 应当, 依照, 本, 法, 规定, 缴纳, 城市, 维护, 建设, 税, 。


验证插件
=======  
用POST请求ES服务  
_analyze  
数据：  
{
  "text": "中华人民共和国城市维护建设税法\n\n\n\n中华人民共和国主席令\n\n（第五十一号）\n\n《中华人民共和国城市维护建设税法》已由中华人民共和国第十三届全国人民代表大会常务委员会第二十一次会议于2020年8月11日通过，现予公布，自2021年9月1日起施行。\n\n中华人民共和国主席习近平\n2020年8月11日\n\n\n中华人民共和国城市维护建设税法\n\n（2020年8月11日第十三届全国人民代表大会常务委员会第二十一次会议通过）\n\n\n\n\n第一条在中华人民共和国境内缴纳增值税、消费税的单位和个人，为城市维护建设税的纳税人，应当依照本法规定缴纳城市维护建设税。\n第二条城市维护建设税以纳税人依法实际缴纳的增值税、消费税税额为计税依据。\n城市维护建设税的计税依据应当按照规定扣除期末留抵退税退还的增值税税额。\n城市维护建设税计税依据的具体确定办法，由国务院依据本法和有关税收法律、行政法规规定，报全国人民代表大会常务委员会备案。\n第三条对进口货物或者境外单位和个人向境内销售劳务、服务、无形资产缴纳的增值税、消费税税额，不征收城市维护建设税。\n第四条城市维护建设税税率如下：\n（一）纳税人所在地在市区的，税率为百分之七；\n（二）纳税人所在地在县城、镇的，税率为百分之五；\n（三）纳税人所在地不在市区、县城或者镇的，税率为百分之一。\n前款所称纳税人所在地，是指纳税人住所地或者与纳税人生产经营活动相关的其他地点，具体地点由省、自治区、直辖市确定。\n第五条城市维护建设税的应纳税额按照计税依据乘以具体适用税率计算。\n第六条根据国民经济和社会发展的需要，国务院对重大公共基础设施建设、特殊产业和群体以及重大突发事件应对等情形可以规定减征或者免征城市维护建设税，报全国人民代表大会常务委员会备案。\n第七条城市维护建设税的纳税义务发生时间与增值税、消费税的纳税义务发生时间一致，分别与增值税、消费税同时缴纳。\n第八条城市维护建设税的扣缴义务人为负有增值税、消费税扣缴义务的单位和个人，在扣缴增值税、消费税的同时扣缴城市维护建设税。\n第九条城市维护建设税由税务机关依照本法和《中华人民共和国税收征收管理法》的规定征收管理。\n第十条纳税人、税务机关及其工作人员违反本法规定的，依照《中华人民共和国税收征收管理法》和有关法律法规的规定追究法律责任。\n第十一条本法自2021年9月1日起施行。1985年2月8日国务院发布的《中华人民共和国城市维护建设税暂行条例》同时废",
  "analyzer": "lawa"
}


插件版本信息  
==========
ES与Lucene版本对应可从``https://raw.githubusercontent.com/elastic/elasticsearch/(7.9)/buildSrc/version.properties```查看


Lawaplugin |  Elasticsearch |  Lucene | Release Link |  
-----------|----------------|---------|--------------|  
lawa 1.0.0|Elasticsearch 6.5.4 |Lucene 7.5.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)    
lawa 1.0.0|Elasticsearch 5.0.3 |Lucene 6.2.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)
lawa 1.0.0|Elasticsearch 5.2.3 |Lucene 6.4.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip) |
lawa 1.0.0|Elasticsearch 5.4.4 |Lucene 6.5.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 5.5.4 |Lucene 6.6.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 5.6.17 |Lucene 6.6.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 6.0.2 |Lucene 7.0.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 6.2.5 |Lucene 7.2.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 6.4.4 |Lucene 7.4.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 6.5.5 |Lucene 7.5.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 6.7.3 |Lucene 7.7.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.0.2 |Lucene 8.0.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.2.2 |Lucene 8.0.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.4.3 |Lucene 8.2.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.5.3 |Lucene 8.3.0 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.7.2 |Lucene 8.5.1 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|
lawa 1.0.0|Elasticsearch 7.9.2 |Lucene 8.6.2 | 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0.zip)|


