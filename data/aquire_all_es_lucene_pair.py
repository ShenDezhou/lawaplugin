import requests
import re

es_pat = "elasticsearch\s+=\s+([\w\.]+)"
lu_pat = "lucene\s+=\s+([\w\.]+)"

ver_list = []
for ver in range(50, 80):
    ver = str(ver * 0.1)
    r = requests.get('https://raw.githubusercontent.com/elastic/elasticsearch/{}/buildSrc/version.properties'.format(ver), timeout=2.5)
    if r.status_code == 200:
        resp = r.text
        matches = re.findall(es_pat,resp)
        es_ver = matches[0]
        matches = re.findall(lu_pat, resp)
        lu_ver = matches[0]
        ver_list.append((es_ver, lu_ver))
        print(es_ver, lu_ver)
print(ver_list)
with open("ver.table",'w') as fw:
    for line in ver_list:
        fw.write(",".join(line))
        fw.write('\n')

print('DONE')


