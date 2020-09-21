

def github_tag_to_mvn_repository(version):
    version_digits = version.split('.')
    version_digits[-1] = str(int(version_digits[-1])-1)
    return ".".join(version_digits)


with open("ver.table",'r') as fw:
    for line in fw:
        line = line.split(' ')
        print('lawa 1.0.0|Elasticsearch',github_tag_to_mvn_repository(line[0]),'|Lucene', line[1].strip(),'| 下载:[1.0.0](https://github.com/ShenDezhou/lawaplugin/releases/download/v1.0.0/lawaplugin-1.0.0-{}.zip)|'.format(github_tag_to_mvn_repository(line[0])))
