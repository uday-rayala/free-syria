# I Support The Syrian Revolution

Helping gather global support for the people suffering under a brutal dictator
in Syria.

Watch this 4 minute video for an intro to the situation:

http://www.youtube.com/watch?v=uYCC6YP6ato&context=C37610b8ADOEgsToPDskLuPHvqOTdNfGEBaX315vDx

## Usage

A noir application serving the site.

## Setting up the code for Rackspace API

See https://github.com/rackspace/java-cloudfiles/issues/22

1. openssl s_client -connect storage101.ord1.clouddrive.com:443 > rackCert

(assuming JDK certs location is

/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/security/cacerts )

2. Trim rackCert file to leave only certificate info.

3. sudo keytool -importcert -keystore /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/security/cacerts -alias storage101.ord1.clouddrive.com -file rackCert
pw: changeit

## License

Copyright (C) 2011 Jeff Rose, Andrew Whitehouse, Udayakumar Rayala

Distributed under the Eclipse Public License, the same as Clojure.

