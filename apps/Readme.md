1. increase sts timeout if meet STS new boot project. add below lines at bottom in sts.ini
-Dcom.sun.xml.internal.ws.connect.timeout=120000
-Dcom.sun.xml.internal.ws.connect.timeout=120000
-Dsun.net.client.defaultReadTimeout=120000
-Dcom.sun.xml.ws.connect.timeout=120000
-Dcom.sun.xml.ws.request.timeout=120000

2. Enable Eclipse Console UTF-8 Support with Jee Server
   Run -> Server -> Common -> Encoding(UTF-8)