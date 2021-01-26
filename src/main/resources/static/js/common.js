layui.use(["okUtils", "okLayer"], function () {
    var okLayer = layui.okLayer;
    //消息推送
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io',
        appkey: "BS-261fbed4d0f249859b554aa16303df49",
    });
    goEasy.subscribe({
        channel: "SPTools",
        onMessage: function (message) {
            okLayer.confirm(message.content);
        }
    });
});