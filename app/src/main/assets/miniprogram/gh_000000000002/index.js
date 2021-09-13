(function (w) {
    var xb = new class { }
    xb.navigateTo = function(src) {
        // var body = document.getElementsByTagName("body")[0]
        // var newIframe = document.createElement("iframe")
        // newIframe.src = src
        // body.appendChild(newIframe)
        // var page0 = document.getElementById("page0")
        // page0.setAttribute("style", "display:none")
        parent.PageTransitions.nextPage(1)
    }
    xb.navigateBack = function(){
        parent.PageTransitions.nextPage(2)
    }
    w.xb = xb;
})(window);