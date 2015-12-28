<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta name="viewport" content="width=device-width; initial-scale=1.0;">
<title>ezModeler</title>
<script type="text/javascript">

	function changeTitle(newTitle) {
	    document.title = newTitle;
	}

    // If loaded bare into a browser, set the browser size to the canvas size
    if (window === top) {
      (function (width, height) {
        // Cf. http://www.quirksmode.org/viewport/compatibility.html
        if (window.innerHeight) {
          // Sadly, innerHeight/Width is not r/w on some browsers, and resizeTo is for outerHeight/Width
          window.resizeTo(width ? (width + window.outerWidth - window.innerWidth) : window.outerWidth,
                          height ? (height + window.outerHeight - window.innerHeight) : window.outerHeight);
        } else if (document.documentElement && document.documentElement.clientHeight) {
          if (width) {
            document.documentElement.clientWidth = width;
          }
          if (height) {
            document.documentElement.clientHeight = height;
          }
        } else {
          if (width) {
            document.body.clientWidth = width;
          }
          if (height) {
            document.body.clientHeight = height;
          }
        }
      })(null, null);
    }
</script>
<script type="text/javascript"
	src="/i4c_lpp/lps/includes/embed-compressed.js"></script>
<style type="text/css">
html,body { /* http://www.quirksmode.org/css/100percheight.html */
	height: 100%;
	/* prevent scrollbars */
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
}

body {
	background-color: #ffffff;
}

img {
	border: 0 none;
}
</style>
</head>
<body>
<script type="text/javascript">
  lz.embed.swf({url: 'main.lzx?lzt=swf&lzr=swf8&lzproxied=solo&autologin=<% out.print(request.getParameter("autologin")); %>', bgcolor: '#ffffff', width: '100%', height: '100%', id: 'lzapp', accessible: 'false'});

  lz.embed.lzapp.onloadstatus = function loadstatus(p) {
    // called with a percentage (0-100) indicating load progress
  }

  lz.embed.lzapp.onload = function loaded() {
    // called when this application is done loading
  }
</script>
<noscript>Please enable JavaScript in order to use this
application.</noscript>
</body>
</html>