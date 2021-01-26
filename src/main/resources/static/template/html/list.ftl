<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head th:replace="common/head :: head(link)"/>
<body>
<div id="app" class="ok-body" v-cloak>
    <!--模糊搜索区域-->
    <template>
        <div style="margin-bottom: 8px;margin-top: 8px;">
            <i-input placeholder="输入内容"  v-model="table.description"  style="width: 300px"></i-input>
            <i-button type="primary"  icon="ios-search" @click="load()">搜索</i-button>
            <i-button type="primary"  icon="ios-redo" @click="reload()" >重置</i-button>
            <i-button type="primary" style="float:right;" icon="md-add" @click="add()">新增</i-button>
        </div>
    </template>
    <template>
        <i-table size="small" :columns="tableTitle"  :data="tableData">
        </i-table>
        <br>
        <Page  style="float: right;" :current="table.pageNo" :total="tableSize" :page-size="table.pageSize"  @on-change="changePage" @on-page-size-change="changePageSize" show-elevator show-sizer show-total></Page>
    </template>
</div>
<div th:replace="common/foot :: foot(script)"></div>
<script th:inline="none">
layui.use(["okUtils", "okLayer"], function () {
    var okUtils = layui.okUtils;
    var okLayer = layui.okLayer;
    var vm = new Vue({
        el: '#app',
        data: function(){
        var that = this;
		return {
            tableTitle : [{
                title: '序号',
                minWidth : 100,
                render: function(h, params) {
                    return h('span', params.index + (that.table.pageNo- 1) * that.table.pageSize + 1);
                }
            },<#list list as entity>{
                key : "${entity.columnName}",
                title : "${entity.columnComment}",
                minWidth:100
            },</#list>{
                title : '操作',
                key : 'action',
                minWidth : 300,
                align : 'center',
                render : function(h, params) {
                   var functionList = [];
                   var edit = h('Button', {
                       props : {
                           type : 'primary',
                           size : 'small',
                           icon : 'md-create'
                       },
                       style : {
                           marginRight : '8px'
                       },
                       on : {
                           click : function() {
                               vm.edit(params.row);
                           }
                       }
                   }, '修改');
                   functionList.push(edit);
                   var remove = h('Button', {
                       props : {
                           type : 'primary',
                           size : 'small',
                           icon : 'md-trash'
                       },
                       style : {
                           marginRight : '8px'
                       },
                       on : {
                           click : function() {
                               vm.remove(params.row);
                           }
                       }
                   }, '删除');
                   functionList.push(remove);
                   return h('div', functionList);
                }
            } ],
            tableData : [],
            table : {
                pageSize : 10,
                pageNo : 1,
                description:''
            },
            tableSize : 0,
          }
        },
        methods: {
            load : function() {
                var that = this;
                okUtils.ajaxCloud({
                    url:"/${prefix}/${function}/list",
                    param : that.table,
                    success : function(result) {
                         that.tableData = result.msg.pageData;
                         that.tableSize = result.msg.totalCount;
                    }
                });
            },
            reload : function(){
                vm.table.pageSize = 10;
                vm.table.pageNo = 1;
                vm.table.description = '';
                this.load();
            },
            changePage : function(pageNo) {
                vm.table.pageNo = pageNo;
                vm.load();
            },
            changePageSize : function(pageSize) {
                vm.table.pageSize = pageSize;
                vm.load();
            },
            add:function(){
                okUtils.dialogOpen({
                    title: '新增',
                    url: "${prefix}/${function}/form.html",
                    scroll : true,
                    width: '30%',
                    height: '45%',
                    yes : function(dialog) {
                        dialog.vm.acceptClick(vm);
                    }
                });
            },
            edit : function(${function}) {
                okUtils.dialogOpen({
                    title: '修改',
                    url: "${prefix}/${function}/form.html",
                    scroll : true,
                    width: '30%',
                    height: '45%',
                    success : function(dialog) {
                        dialog.vm.${function} = JSON.parse(JSON.stringify(${function}));
                    },
                    yes : function(dialog) {
                        dialog.vm.acceptClick(vm);
                    }
                });
            },
            remove : function (${function}) {
                 okLayer.confirm("确定要删除吗？", function () {
                    okUtils.ajaxCloud({
                        url:"/${prefix}/${function}/delete",
                        param : {id: ${function}.id},
                        success : function(result) {
                            okLayer.msg.greenTick(result.msg, function () {
                                 vm.load();
                            });
                        }
                    });
                 });
            },
        },
        created: function() {
            this.load()
        }
    })
  });
</script>
</body>
</html>
