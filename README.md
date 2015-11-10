# JFX-Overlays
JavaFX Dynamic Graphics Canvas for use with a chroma-key on live sports casting. Currently supports full projection of Twitter data onto the canvas along with a scoreboard, videos, and animated static graphics. Easily add other RESTful sources as you see fit. Note the source is very rough and needs some refactoring. This was thrown together quickly for rapid integration into a high school football broadcasting pipeline that needed it immediately. 

# Dependencies: 
All are found in the libs folder. 
(1) javax.json-1.0.4 for parsing RESTful responses
(2) Apache's rest_http library

Also note that you need a folder in your project directory called "resources", which is where you will drop your assets for JavaFX to load and draw. 

** I highly recommend you make a new JavaFX project in your favorite IDE and port all the sources into it manually so that your build process is fresh and smooth. Currently the Intellij project as is will not build (I needed to rename it hastily). 
