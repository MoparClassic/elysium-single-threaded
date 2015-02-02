# Elysium

## Overview
Elysium is a Runescape Classic server emulator. The goal of this project is to match the RSCD project in terms of
feature completeness while providing an improved codebase.

Continuous integration can be found here: https://travis-ci.org/Lothy/elysium-single-threaded
IRC server: irc.moparisthebest.com
IRC channels: #elysium #elysium-dev
Forum topic: https://www.moparscape.org/smf/index.php/topic,668364.20.html

This server makes use of the RSCDv25 mudclient.



## Features currently under development
* Script manager
* Packet handling code



## Dependencies
* Hibernate c3p0 - 4.3.8.Final
* Netty 4.0.2.5.Final
* TestNG 6.1.1
* Xstream 1.4.7

See the Maven pom.xml file for more details.d
Database access is not yet implemented. However an appropriate JDBC driver is required for use with Hibernate.



## Contributors
* Daniel Loth (Lothy)
* Graham Edgecombe (code borrowed from Lightstone)

