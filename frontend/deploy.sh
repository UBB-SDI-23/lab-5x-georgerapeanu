API_URL=$MANUAL_API_URL;

if [ ! -z $INCOMING_HOOK_BODY ] ; then
    API_URL=$INCOMING_HOOK_BODY;
fi;

export API_URL;

for i in ./src/environments/*.tmpl;do 
    cat $i | envsubst > ${i%.*};
done; 

cat ./src/_redirects.tmpl | envsubst > ./src/_redirects

ng build --configuration=production;