"use strict";
layui.use(["element","okUtils", "table", "countUp"], function () {
    var countUp = layui.countUp;
    var table = layui.table;
    var okUtils = layui.okUtils;
    var $ = layui.jquery;

    /**
     * 收入、商品、博客、用户
     */
    function statText() {
        var elem_nums = $(".stat-text");
        elem_nums.each(function (i, j) {
            var ran = parseInt(Math.random() * 99 + 1);
            !new countUp({
                target: j,
                endVal: 20 * ran
            }).start();
        });
    }

    var userSourceOption = {
        "title": {"text": ""},
        "tooltip": {"trigger": "axis", "axisPointer": {"type": "cross", "label": {"backgroundColor": "#6a7985"}}},
        "legend": {"data": ["邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"]},
        "toolbox": {"feature": {"saveAsImage": {}}},
        "grid": {"left": "3%", "right": "4%", "bottom": "3%", "containLabel": true},
        "xAxis": [{"type": "category", "boundaryGap": false, "data": ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]}],
        "yAxis": [{"type": "value"}],
        "series": [
            {"name": "邮件营销", "type": "line", "stack": "总量", "areaStyle": {}, "data": [120, 132, 101, 134, 90, 230, 210]},
            {"name": "联盟广告", "type": "line", "stack": "总量", "areaStyle": {}, "data": [220, 182, 191, 234, 290, 330, 310]},
            {"name": "视频广告", "type": "line", "stack": "总量", "areaStyle": {}, "data": [150, 232, 201, 154, 190, 330, 410]},
            {"name": "直接访问", "type": "line", "stack": "总量", "areaStyle": {"normal": {}}, "data": [320, 332, 301, 334, 390, 330, 320]},
            {"name": "搜索引擎", "type": "line", "stack": "总量", "label": {"normal": {"show": true, "position": "top"}}, "areaStyle": {"normal": {}}, "data": [820, 932, 901, 934, 1290, 1330, 1320]}
        ]
    };

    /**
     * 用户访问
     */
    function userSource() {
        var userSourceMap = echarts.init($("#userSourceMap")[0], "theme");
        userSourceMap.setOption(userSourceOption);
        okUtils.echartsResize([userSourceMap]);
    }

    var userLocationOption = {
        "title": {"text": "用户家庭所在地统计", "subtext": "", "x": "center"},
        "tooltip": {"trigger": "item"},
        "visualMap": {
            "color": ["#eeeeee"], "show": false, "x": "left", "y": "center",
            "splitList": [
                {"start": 500, "end": 600},
                {"start": 400, "end": 500},
                {"start": 300, "end": 400},
                {"start": 200, "end": 300},
                {"start": 100, "end": 200},
                {"start": 0, "end": 100}
            ]
        },
        "series": [
            {
                "name": "用户家庭所在地统计", "roam": true, "type": "map", "mapType": "china", "data": [],
                "itemStyle": {
                    "normal": {"areaColor": "#eeeeee", "borderColor": "#aaaaaa", "borderWidth": 0.5},
                    "emphasis": {"areaColor": "rgba(63,177,227,0.25)", "borderColor": "#3fb1e3", "borderWidth": 1}
                },
                "label": {"normal": {"textStyle": {"color": "#000"}}, "emphasis": {"textStyle": {"color": "#000"}}}
            }
        ]
    };
    statText();
    userSource();
});


