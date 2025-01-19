(function() {
    if (!window.kowe) {
        window.kowe = {};
    }

    window.kowe.postMessage = function(message) {
        if (typeof message === "string") {
            if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.kowe) {
                // iOS
                window.webkit.messageHandlers.kowe.postMessage(message);
            } else if (window.koweNative) {
                // Android
                window.koweNative.postMessage(message);
            } else if (window.cefNative) {
                // Desktop
                window.cefNative.postMessage(message);
            } else {
                console.warn("Kowe postMessage API is not supported on this platform.");
            }
        } else {
            console.error("Kowe postMessage expects a string message.");
        }
    };
})();
