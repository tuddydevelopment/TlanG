  TlanG
  ^Tuddy
   ^lang
      ^(for) (code-)Golfing


Usage:

Hello world:
  Hello world
aaaaa:
  a*5
Hello Hello Hello:
  (Hello)*3
<clear terminal>:
  (#-')*511

Commands:
  <any char> print
  <any char>*<number smaller than 512>                                               
    - print multiple
  (<any string (can contain commands, but no other loops>)*<number smaller than 512> 
    - print string multiple times
  \<any command>                                                                     
    - print the command, dont execute it
  '                                                                                  
    - go up in terminal (must be compatible with terminal, any linux terminal works)
  ,                                                                                  
    - go down in terminal (must be compatible with terminal, any linux terminal works)
  <                                                                                  
    - go left in terminal (must be compatible with terminal, any linux terminal works)
  >                                                                                  
    - go right in terminal (must be compatible with terminal, any linux terminal works)
  #                                                                                  
    - go to start of line in terminal (must be compatible with terminal, any linux terminal works)
  -                                                                                  
    - delete everything behind the cursor (must be compatible with terminal, any linux terminal works)
  \n                                                                                 
    - line break (program line breaks are ignored)

Running:
  Download the latest JAR
  Run it using `java -jar TlanG.jar <FILE_TO_RUN>
