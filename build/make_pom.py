import os
import subprocess

os.chdir("..")
COMMAND_BUILD = [r'D:\Program Files\apache-maven-3.6.3\bin\mvn', "-DskipTests",'package']
MV_RELEASE = r'''copy target\releases\lawaplugin-1.0.0.zip releases\lawaplugin-1.0.0-{}.zip'''

ver_list = [("6.5.5", "7.5.0")]
with open('data/ver.table','r') as ft:
    for line in ft:
        ver_list.append(line.strip().split(' '))
print(ver_list)

def github_tag_to_mvn_repository(version):
    version_digits = version.split('.')
    version_digits[-1] = str(int(version_digits[-1])-1)
    return ".".join(version_digits)

for es_ver, lucene_ver in ver_list:
    print(es_ver, lucene_ver)
    with open('build/pom.xml', 'r') as fr:
        with open('pom.xml', 'w') as fw:
            count = 0
            for line in fr:
                if count<2:
                    if 'elasticsearch.version' in line:
                        fw.write('<elasticsearch.version>{}</elasticsearch.version>\n'.format(github_tag_to_mvn_repository(es_ver)))
                        count+=1
                    elif 'lucene.version' in line:
                        fw.write('<lucene.version>{}</lucene.version>\n'.format(lucene_ver))
                        count+=1
                    else:
                        fw.write(line)
                else:
                    fw.write(line)

        subprocess.call(COMMAND_BUILD, shell=True)
        os.system('"' + MV_RELEASE.format(github_tag_to_mvn_repository(es_ver))+ '"')
os.system("copy build\pom.xml /y")
print('DONE')