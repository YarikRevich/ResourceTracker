#!/bin/bash

# Creates binary file of CLI ResourceTracker app
# Makes it available to use in command line

touch /usr/local/bin/resourcetracker
chmod +x /usr/local/bin/resourcetracker
echo '' > /usr/local/bin/resourcetracker
echo '#!/bin/bash' >> /usr/local/bin/resourcetracker
echo 'java -jar cli/target/lib/cli*' >> /usr/local/bin/resourcetracker
