# This directory contains the sample apache configuration for the AEM dispatcher

## Download Apache webserver from the following URL (httpd-2.2.22-win32-x86-no_ssl.msi)

https://archive.apache.org/dist/httpd/#archive

## Download the associated dispatcher from the following URL (dispatcher-apache2.2-windows-x86-4.2.3.zip)

https://www.adobeaemcloud.com/content/companies/public/adobe/dispatcher/dispatcher.html 
    
## Configure the httpd.conf. Below are some of the settings that were configured

ServerRoot "C:/Program Files (x86)/Apache Software Foundation/Apache2.2"
Listen 81
LoadModule dispatcher_module  modules/disp_apache2.2.dll
DispatcherLogLevel 4
<Directory "C:/Program Files (x86)/Apache Software Foundation/Apache2.2/htdocs">

## Configure the dispatcher.any. Below are some of the settings that were configured

    # The load will be balanced among these render instances
    /renders
      {
      /rend01
        {
        # Hostname or IP of the render
        /hostname "127.0.0.1"
        # Port of the render
        /port "8080"
        # Connect timeout in milliseconds, 0 to wait indefinitely
        # /timeout "0"
        }
      }

    /filter
      {
      # Deny everything first and then allow specific entries
      /0001  { /glob "*" /type "allow" }
      }

## To monitor the filter. Set the DispatcherLogLevel to 4 (trace) in httpd.conf

cd /c/Program Files (x86)/Apache Software Foundation/Apache2.2/logs
tail -f dispatcher.log | grep --line-buffered 'Filter\|blocked'


Malcolm Langille