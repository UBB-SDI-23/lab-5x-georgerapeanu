API_URL=$MANUAL_API_URL;

echo "debug " $MANUAL_API_URL;

if [ ! -z $INCOMING_HOOK_BODY ] ; then
    API_URL=$INCOMING_HOOK_BODY;
fi;

for i in ./src/environments/*.tmpl;do 
    cat $i | envsubst > ${i%.*};
done; 

ng build --configuration=production;