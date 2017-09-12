// nav左侧导航滑动效果
$(function () {
    $('#nav').on('click', '.sidebar-title', function () {
        $(this).next().stop().slideToggle().siblings('.sidebar-content').stop().slideUp();
        $(this).children('span').toggleClass('current').end().siblings('.sidebar-title').children('span').removeClass('current');
    });
    $('#nav .sidebar-content').on('click','li',function () {
        $(this).addClass('current').siblings().removeClass('current').end().parent().siblings('.sidebar-content').children('li').removeClass('current');
    });
});
(function (window, document) {
    // 定义全局方法 父组件通过此方法得到子组件的 当前选择的省市区、幼儿园、年月日、 跳转到第几页
    Vue.prototype.$getCurrentKindergarten = function (kindergarten) {
        this.current_kindergarten_id = kindergarten;
    };
    Vue.prototype.$getGoPage = function (goPage) {
        this.pageNo = goPage;
    };
    
    //年月日组件
    Vue.component('public-date', {
        template:
            '<div class="date">' +
                '<select class="year" v-model="current_year">' +
                    '<option>全部</option>'+
                    '<option v-for="year in years">{{year + "年"}}</option>'+
                '</select>' +
                '<select class="month" v-model="current_month" v-show="current_year !== '+"'全部'"+'">' +
                    '<option>全部</option>'+
                    '<option v-for="month in months">{{month + "月"}}</option>'+
                '</select>' +
                // '<select class="week" v-model="current_week">' +
                //     '<option>全部</option>'+
                //     '<option v-for="week in weeks">{{"第" + week + "周"}}</option>'+
                // '</select>' +
                '<select class="day" v-model="current_day">' +
                    '<option>全部</option>'+
                    '<option v-for="day in days">{{day + "日"}}</option>'+
                '</select>' +
            '</div>',
        data:function () {
           var i=0,
               years = [],
               months = [1,2,3,4,5,6,7,8,9,10,11,12],
               now = new Date(), //服务器时间为准
               nowYear = now.getFullYear(),
               nowMonth = now.getMonth()+1,
               nowday = now.getDate();
               // nowWeek = Math.ceil(nowday/7);
           for(i=nowYear;i>2013;i--){
               years[years.length] = i;
           }
           return {
               years: years,
               months: months,
               current_year: nowYear + '年',
               current_month: nowMonth + '月',
               // current_week:'第'+ nowWeek + '周',
               current_day: nowday + '日'
           }
       },
        computed:{
            // weeks:function () {
            //     var current_month = parseInt(this.current_month),
            //         current_year = parseInt(this.current_year),
            //         arr = [],
            //         i = 1;
            //         len = 0;
            //     if(current_month !== 2 || current_year % 4 === 0){
            //         len = 5;
            //     }else{
            //         len = 4
            //     }
            //     for(i=1;i<=len;i++){
            //         arr[arr.length] = i;
            //     }
            //     return arr;
            // },
            days:function () {
                var current_month = parseInt(this.current_month),
                    current_year = parseInt(this.current_year),
                    arr = [],
                    i = 1,
                    len = 0;
                switch(current_month){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        len = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        len = 30;
                        break;
                    case 2:
                        if(current_year % 4 === 0){
                            len = 29;
                        }else{
                            len = 28;
                        }
                        break;
                }
                for(i=1;i<=len;i++){
                    arr[arr.length] = i;
                }
                return arr ;
            },
            //计算当前选择的时间戳(单位:秒)
            current_timestamp:function () {
                var year = parseInt(this.current_year),
                    month = parseInt(this.current_month),
                    day = parseInt(this.current_day),
                    timestamp = new Date(year+'-'+month+'-'+day);
                return timestamp.getTime()/1000 //以秒为单位
            }
        },
        methods: {
            giveTimes:function () {
                this.$emit('giveTimes',this.current_timestamp);
            }
        },
        watch: {
            current_timestamp:function () {
                this.giveTimes();
            }
        },
        created:function () {
            this.giveTimes();
        }
    });
    //幼儿园组件
    Vue.component('public-kindergartens',function (resolve,reject) {
        //假数据 实际以全局的省市区进行请求
        $.getJSON('data/kindergartens.json',function (data) {   //假数据
            kindergartens.data = data; //获取幼儿园名字和ID 将ID隐藏
            resolve({
                template:
                '<select @change="getID($event)" v-model="current_kindergarten_name">' +
                    '<option >幼儿园</option>' +
                    '<option v-for="kindergarten in data" :value="kindergarten.id">{{kindergarten.name}}</option>' +
                '</select>',
                data:function () {
                    return kindergartens;
                },
                computed:{
                    getData:function () {
                        return address3.current_province + address3.current_city + address3.current_counties;
                    }
                },
                methods:{
                    getID:function (e) {
                        var sel = e.target,
                            index = sel.selectedIndex;
                        this.current_kindergarten_id = sel[index].value;
                    }
                },
                watch:{
                    getData:function () {
                        console.log('省市区发生变化，重新请求幼儿园列表');
                    }
                }
            });
        })

    });
    //年级+班级组件
    Vue.component('public-gradeAndClass',function (resolve,reject) {
        $.getJSON('data/gradeAndClass.json',function (data) {
            resolve({
                props:['hideClass'],
                template:
                '<div class="gradeAndClass inlineBlock">' +
                '<select v-model="current_grade"  @change="setDefault_value">' +
                '<option>年级</option>'+
                '<option v-for="(value,grade) in data">{{grade}}</option>'+
                '</select>'+
                '<select v-model="current_class" v-show="current_grade !== '+"'年级'"+'&& hideClass===undefined">' +
                '<option>班级</option>'+
                '<option v-for="value in data[current_grade]">{{value}}</option>'+
                '</select>'+
                '</div>',
                data:function () {
                    return {
                        data:data,
                        current_grade: '年级',
                        current_class: '班级',
                    }
                },
                methods:{
                    setDefault_value:function () {
                        this.current_class = '班级';
                    },
                    giveGradeAndClass:function () {
                        this.$emit('giveGradeAndClass',this.current_grade,this.current_class);
                    }
                },
                computed:{
                    getData:function () {
                        return this.current_kindergarten_id;
                    },
                    //当前年级或班级发生变化
                    current_gradeAndClass:function () {
                        return this.current_grade + this.current_class;
                    }
                },
                watch:{
                    getData: function () {
                        console.log('幼儿园ID发生变化，重新请求班级');
                    },
                    current_gradeAndClass:function () {
                        this.giveGradeAndClass();
                    }
                }
            });
        });
    });
    //还没写  生成统计图 柱状和折线
    Vue.component('public-statistics', {});
    //表格组件 利用props属性拿到父组件数据 title（[]） item（[{},{}]）后台给的需要按顺序排列
    Vue.component('public-table', {
        props: ['title', 'item','itemCount','type'], //kindergarten、parents属性：表格是否显示可编辑
        template: '<table>' +
        '<thead>' +
        '<tr>' +
        '<th v-for="th in title ">{{th}}</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        '<tr v-for="(tr,index) in item">' +
            '<td v-if="type ==='+"'monitor'"+'"><input  type="checkbox" name="monitor" class="monitor"></td>'+
        '<td v-for="data in tr">{{data}}</td>' +// monitorState: '未开通',   attendanceState: '未开通',
            // //表格后面的操作选项
            //隐藏了倒数第二个td 倒数第二个td如果存在operation属性 就显示下面这个td 如果点击了一个元素 则返回index和span中的值
            '<td  v-if="tr.operation"><span v-for="value in tr.operation.action" @click="getOperationObj(tr.operation.type,index,value)">{{value}}</span></td>'+
            // '<td class="operation" v-if="type ==='+"'kindergartenManage'"+'"><span>查看</span><span>修改</span><span>冻结</span><span>禁用账号</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'infoManage_parents'"+'"><span @click="showDetailInfo(tr.id)">查看</span><span v-show="tr.monitorState==='+"'未开通'"+'">开通视频</span><span v-show="tr.attendanceState==='+"'未开通'"+'">开通考勤</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'infoManage_parents_baby'"+'"><span>查看</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'infoManage_babys'"+'"><span>查看</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'infoManage_teachers'"+'"><span>查看</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'infoManage_kindergartens'"+'"><span>查看</span></td>'+
            // '<td class="operation" v-else-if="type ==='+"'monitor'"+'"><span>开通视频功能</span></td>'+
        '</tr>' +
        '<tr  v-for="n in itemCount">' +
        '<td  v-for="n in title.length"></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>',
        methods:{
            getOperationObj:function (type,index,value) {
                this.$emit('getOperationObj',type,index,value);
            }
        }
    });
    // 分页组件
    Vue.component('public-paging', {
        props: ['pages'], //用来接收总共多少页
        template: '<div @click="goPage($event)" class="paging">' +
        '<input  type="button" value="首页" class="first">' +
        '<input  type="button" value="上一页" class="prev">' +
        '<input  type="button" value="下一页" class="next">' +
        '<input  type="button" value="尾页" class="last">' +
        '当前第 <span>{{pageNo}}</span> / <span>{{pageSum}}</span>页' +
        '<input class="input-go" type="text" v-model=goNo>' +
        '<input  class="go" type="button" value="go">' +
        '</div>',

        data: function () {
            return {
                pageNo: 1, //记录当前页数
                goNo: '',   //直接前往第几页
                pageSum: this.pages || 1,
            }
        },
        methods: {
            goPage: function (e) {
                switch (e.target.className) {
                    case 'first':
                        this.pageNo = 1;
                        break;
                    case 'prev' :
                        this.pageNo--;
                        break;
                    case 'next':
                        this.pageNo++;
                        break;
                    case 'last':
                        this.pageNo = this.pageSum;
                        break;
                    case 'go':
                        this.pageNo = parseInt(this.goNo);
                        if (this.pageNo !== this.pageNo) {
                            alert('请输入正确的数字');
                        }
                        break;
                }
                this.pageNo = this.pageNo < 1 ? 1 : this.pageNo;
                this.pageNo = this.pageNo > this.pageSum ? this.pageSum : this.pageNo;
                this.$emit('goPage', this.pageNo); //提交当前页数给父组件
            }
        }
    });
    /*---------------------------------------*/
    // 路由-统计
    var infoStatistics = Vue.component('info-statistics', {
        template:
        '<div class="newStatistics">' +

                '<div class="filter">'+
                    '<select  v-model="current_StatisticsType">' +
                        '<option>新增统计</option>' +
                        '<option>新增详情</option>' +
                        '<option>删除统计</option>' +
                        '<option>删除详情</option>' +
                        '<option>晨检统计</option>' +
                        '<option>考勤统计</option>' +
                    '</select>'+
                    '<div v-show="current_StatisticsType === '+"'新增详情'"+' || current_StatisticsType === '+"'删除详情'"+'" class="inlineBlock">' +
                        '<public-address3></public-address3>' +
                        '<public-kindergartens :kindergartens="kindergartens" v-on:getCurrentKindergarten="getCurrentKindergarten"></public-kindergartens>' +
                        '<select v-model="current_personType">' +
                        '<option>全部</option>' +
                        '<option>宝宝</option>' +
                        '<option>家长</option>' +
                        '<option>老师</option>' +
                        '</select>'+
                    '</div>'+
                    '<public-date></public-date>'+
                '</div>'+
            '<public-table :title="current_StatisticsData.tableTitle" :item="current_StatisticsData.tableItem" :itemCount="16-current_StatisticsData.tableItem.length" ></public-table>' +
            '<public-paging :pages="pages" v-on:goPage="getGoPage"></public-paging>' +
        '</div>',
        data: function () {
            return {
                //search部分
                current_StatisticsType:'', //created 时变为新增数据 即直接向服务器请求数据
                current_personType: '全部',
                kindergartens: ['红太阳幼儿园', '红苹果幼儿园', '绿帽子幼儿园'],//假数据
                pageNo: 1,
                pages: Math.ceil(50 / 16), //假数据
                newStatistics:{
                    tableTitle:['宝宝','老师','家长'],
                    tableItem:[
                            {
                                babyNumber:600,
                                teacherNumber:20,
                                patriarch:400
                            }
                        ]
                },
                newDetail:{
                    tableTitle: ['幼儿园', '幼儿园地址', '新增类型', '姓名', '新增时间'],
                    tableItem: [ //假数据
                        {
                            kindergarten: '红太阳幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '张三',
                            addTime: '2017/07/06'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        },
                        {
                            kindergarten: '红苹果幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '李四',
                            addTime: '2017/07/07'
                        }
                    ]
                },
                deleteStatistics:{
                    tableTitle:['宝宝','老师','家长'],
                    tableItem:[
                        {
                            babyNumber:100,
                            teacherNumber:4,
                            patriarch:65
                        }
                    ]
                },
                deleteDetail:{
                    tableTitle: ['幼儿园','幼儿园地址','删除类型','姓名', '删除时间'],
                    tableItem:[
                        {
                            kindergarten: '红太阳幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '张三',
                            deleteTime: '2017/07/06'
                        },
                        {
                            kindergarten: '红太阳幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '张三',
                            deleteTime: '2017/07/06'
                        },
                        {
                            kindergarten: '红太阳幼儿园',
                            address: '温州市xxx',
                            type: '宝宝',
                            name: '张三',
                            deleteTime: '2017/07/06'
                        }
                    ]
                },
                morningCheckStatistics:{
                    tableTitle: ['晨检总人数','晨检异常(≥37℃)'],
                    tableItem:[
                        {
                            morningCheckSum: 600,
                            abnormal:6,
                        }
                    ]
                },
                RegulationStatistics:{
                    tableTitle: ['上午应打卡人数','上午已打卡人数','下午应打卡人数','下午已打卡人数'],
                    tableItem: [
                            {
                                amShouldCheck:600,
                                amCheck:590,
                                pmShouldCheck:598,
                                pmCheck: 588
                            }
                        ]
                }
            };
        },
        methods: {
            getCurrentKindergarten: Vue.prototype.$getCurrentKindergarten,
            getGoPage: Vue.prototype.$getGoPage,
        },
        computed: {
            current_StatisticsData:function () { //当前数据类型
                switch(this.current_StatisticsType){
                    case '新增统计':
                        return JSON.parse(JSON.stringify(this.newStatistics));
                    case '新增详情':
                        return JSON.parse(JSON.stringify(this.newDetail));
                    case '删除统计':
                        return JSON.parse(JSON.stringify(this.deleteStatistics));
                    case '删除详情':
                        return JSON.parse(JSON.stringify(this.deleteDetail));
                    case '晨检统计':
                        return JSON.parse(JSON.stringify(this.morningCheckStatistics));
                    case '考勤统计':
                        return JSON.parse(JSON.stringify(this.RegulationStatistics));
                }
            },
            getData: function () {
                switch(this.current_StatisticsType){
                    //年月日、当前页 等还没写
                    case '新增详情': //请求数据 ?province = this.current_province&city = this.current_city...
                        return this.current_StatisticsType+address3.current_province + address3.current_city + address3.current_counties+kindergartens.current_kindergarten_id +this.current_personType+this.pageNo
                    case '删除统计': //url=xxx
                    case '晨检统计': //url=xxx
                }
            },
        },
        watch: {
            getData: function () {
                // 数据发生变化时$.ajax() 并计算总共有多少页
                console.log(this.getData);
            },
            current_StatisticsType:function () {
                document.title = this.current_StatisticsType
            }
        },
        beforeCreate:function () {
            document.title = '新增统计';
        },
        created:function () {
            this. current_StatisticsType = '新增详情'
        }
    });
    // 路由-用户信息管理
    var infoManage = Vue.component('info-manage',{
        template:
        '<div class="infoManage">'+
                '<div class="filter">'+
                    '<div>' +
                        '<select v-model="current_usersType">' +
                            '<option>家长信息管理</option>'+
                            '<option>宝宝信息管理</option>'+
                            '<option>老师信息管理</option>'+
                            '<option>幼儿园信息管理</option>'+
                        '</select>'+
                        '<public-address3></public-address3>'+
                        '<public-kindergartens v-show="current_usersType !=='+"'幼儿园信息管理'"+'"></public-kindergartens>'+
                    '</div>'+
                    '<div v-show="current_usersType !=='+"'幼儿园信息管理'"+'" class="inlineBlock">姓名: <input v-model="name" class="name" type="text"></div>'+
                    '<div v-show="current_usersType ==='+"'幼儿园信息管理'"+'" class="inlineBlock">幼儿园名: <input v-model="name" class="name" type="text"></div>'+
                    '<div v-show="current_usersType !=='+"'宝宝信息管理'"+'" class="inlineBlock">' +
                        '手机号: <input v-model="phoneNumber" type="text">'+
                    '</div>'+
                    '<div v-show="current_usersType ==='+"'家长信息管理'"+' || current_usersType ==='+"'幼儿园信息管理'"+'" class="inlineBlock">' +
                        '视频功能: '+
                        '<select v-model="monitor">'+
                        '<option>请选择</option>'+
                        '<option>未开通</option>'+
                        '<option>已开通</option>'+
                        '</select>'+
                        '考勤功能: '+
                        '<select v-model="attendance">'+
                        '<option>请选择</option>'+
                        '<option>未开通</option>'+
                        '<option>已开通</option>'+
                        '</select>'+
                    '</div>' +
                    '<div v-show="current_usersType ==='+"'宝宝信息管理'"+'" class="inlineBlock">' +
                        '年级: ' +
                        '<select>'+
                        '<option>请选择</option>'+
                        '<option>大班(名字自动生成)</option>'+
                        '<option>二班(名字自动生成)</option>'+
                        '</select>'+
                        '班级: '+
                        '<select>'+
                        '<option>请选择</option>'+
                        '<option>1班(名字自动生成)</option>'+
                        '<option>小班(名字自动生成)</option>'+
                        '</select>'+
                    '</div>'+
                '</div>'+
                '<public-table :title="current_usersData.tableTitle" :item="current_usersData.tableItem" :itemCount="16 - current_usersData.tableItem.length" v-on:getOperationObj="operation"></public-table>'+
                '<public-paging :pages="pages" v-on:goPage="getGoPage"></public-paging>' +
            //家长详细信息
            '<div class="look-Detaildata" v-if="showDetail === '+"'patriarch'"+'">'+
                '<h3>家长详细信息: </h3>'+
                '<div>姓名: <input type="text" value="" disabled></div>'+
                '<div>详细地址: <input type="text" value="" disabled></div>'+
                '<div>手机号: <input disabled type="text" value=""></div>'+
                '<div>密码: <input type="text" disabled></div>'+
                '<div>视频功能开启状态: <input type="text" disabled></div>'+
                '<div>考勤功能开启状态: <input type="text" disabled></div>'+
                '<div class="look"><input @click="closeDetail" class="clear" type="button" value="关闭"></div>'+
            '</div>'+
            //宝宝详细信息
            '<div class="look-Detaildata" v-if="showDetail === '+"'baby'"+'">'+
                '<h3>宝宝详细信息: </h3>'+
                '<div>姓名: <input type="text" value="" disabled></div>'+
                '<div>学号: <input type="text" value="" disabled></div>'+
                '<div>所在幼儿园ID: <input  type="text" value="" disabled></div>'+
                '<div>考勤卡卡号: <input  type="text" value="" disabled></div>'+
                '<div>带班老师: <input  type="text" value="" disabled></div>'+
                '<div>年级: <input type="text" disabled></div>'+
                '<div>班级: <input type="text" disabled></div>'+
                '<div>监护人: <input type="text" disabled></div>'+
                '<div>监护人关系: <input type="text" disabled></div>'+
                '<div>监护人手机号: <input type="text" disabled></div>'+
                '<div class="look"><input @click="closeDetail" class="clear" type="button" value="关闭"></div>'+
            '</div>'+
            //老师详细信息
            '<div class="look-Detaildata" v-if="showDetail === '+"'teacher'"+'">'+
                '<h3>老师详细信息: </h3>'+
                '<div>姓名: <input type="text" value="" disabled></div>'+
                '<div>性别: <input type="text" value="" disabled></div>'+
                '<div>年龄: <input type="text" value="" disabled></div>'+
                '<div>学历: <input type="text" value="" disabled></div>'+
                '<div>教师资格证: <input type="text" value="" disabled></div>'+
                '<div>普通话: <input type="text" value="" disabled></div>'+
                '<div>所在幼儿园ID: <input  type="text" value="" disabled></div>'+
                '<div>考勤卡卡号: <input  type="text" value="" disabled></div>'+
                '<div>教职工编号: <input  type="text" value="" disabled></div>'+
                '<div>所带班级: <input  type="text" value="" disabled></div>'+
                '<div>手机号: <input  type="text" value="" disabled></div>'+
                '<div>密码: <input  type="text" value="" disabled></div>'+
                '<div class="look"><input @click="closeDetail" class="clear" type="button" value="关闭"></div>'+
            '</div>'+
            //幼儿园详细信息
            '<div class="look-Detaildata" v-if="showDetail === '+"'kindergarten'"+'">'+
                '<h3>幼儿园详细信息</h3>'+
                '<div>幼儿园名: <input type="text" value="xxx幼儿园" disabled></div>'+
                '<div>幼儿园ID: <input type="text" value="#123456" disabled></div>'+
                '<div>签约人: <input type="text" value="" disabled></div>'+
                '<div>幼儿园归属人联系方式: <input type="text" disabled></div>'+
                '<div>合同编号: <input type="text" value="" disabled></div>'+
                '<div>合同起始日期: <input type="text" value="" disabled></div>'+
                '<div>合同截止日期: <input type="text" value="" disabled></div>'+
                '<div>组织编码索引: <input type="text" value=""disabled></div>'+
                '<div>注册时间: <input type="text" disabled></div>'+
                '<div>省份: <input type="text" disabled></div>'+
                '<div>城市: <input type="text" disabled></div>'+
                '<div>县区: <input type="text" disabled></div>'+
                '<div>详细地址: <input type="text" disabled></div>'+
                '<div>冻结状态: <input type="text" disabled></div>'+
                '<div>手机号: <input type="text" disabled></div>'+
                '<div>幼儿园收费标准: <input type="text" disabled></div>'+
                '<div>幼儿园视频开通状况: <input type="text" disabled></div>'+
                '<div>幼儿园考勤开通状况: <input type="text" disabled></div>'+
                '<div class="look"><input @click="closeDetail" class="clear" type="button" value="关闭"></div>'+
            '</div>'+
        '</div>', //infoManage
        data:function () {
            return {
                current_usersType: '', //页面加载默认加载家长信息管理从而触发监听事件
                showDetail: '',  //是否显示详细信息
                pages:6,
                pageNo:1,
                name:'',
                phoneNumber:'',
                monitor: '未开通', //监控功能
                attendance:'未开通', //考勤功能
                //家长数据
                parents: {
                    tableTitle: ['姓名', '手机号', '视频功能', '考勤功能', '隐藏','操作'],
                    tableType: 'infoManage_parents',
                    tableItem: [ //假数据
                        {
                            name: '张3',
                            phoneNumber: 13912341234,
                            monitorState: '已开通',
                            attendanceState: '已开通',
                            operation:{
                                type: '家长',
                                action:['查看']
                            }
                        },
                        {
                            name: '张4',
                            phoneNumber: 13912341234,
                            monitorState: '未开通',
                            attendanceState: '未开通',
                            operation:{
                                type: '家长',
                                action:['查看']
                            }
                        }
                    ]

                },
                //宝宝数据
                babys: {
                    tableTitle: ['姓名', '性别', '年级', '班级','隐藏', '操作'],
                    tableType: 'infoManage_babys',
                    tableItem: [ //假数据
                        {
                            name: '李白',
                            sex: '男',
                            grade: '中班',
                            class: '1班',
                            operation:{
                                type: '宝宝',
                                action:['查看']
                            }

                        },
                        {
                            name: '杜甫',
                            sex: '男',
                            grade: '大班',
                            class: '2班',
                            operation:{
                                type: '宝宝',

                                action:['查看']
                            }
                        },
                        {
                            name: '李清照',
                            sex: '女',
                            grade: '小班',
                            class: '3班',
                            operation:{
                                type: '宝宝',

                                action:['查看']
                            }
                        }
                    ],
                },
                //老师数据
                teachers: { //假数据
                    tableTitle:['姓名', '性别', '年龄', '手机','隐藏', '操作'],
                    tableType: 'infoManage_teachers',
                    tableItem:[
                        {
                            name: '小乔',
                            sex: '女',
                            age: '23',
                            phoneNumber: 13912345678,
                            operation:{
                                type: '老师',
                                action:['查看']
                            }
                        },
                        {
                            name: '甄氏',
                            sex: '女',
                            age: '22',
                            phoneNumber: 13912345678,
                            operation:{
                                type: '老师',
                                action:['查看']
                            }
                        },
                        {
                            name: '大乔',
                            sex: '女',
                            age: '24',
                            phoneNumber: 13912345678,
                            operation:{
                                type: '老师',
                                action:['查看']
                            }
                        },
                        {
                            name: '貂蝉',
                            sex: '女',
                            age: '23',
                            phoneNumber: 13912345678,
                            operation:{
                                type: '老师',
                                action:['查看']
                            }
                        }
                    ]
                },
                // 幼儿园数据
                kindergartens:{
                    tableTitle:['幼儿园名', '手机号', '视频功能', '考勤功能','隐藏', '操作'],
                    tableType: 'infoManage_kindergartens',
                    tableItem:[
                        {
                            name: '红太阳幼儿园',
                            phoneNumber: 13512341234,
                            monitorState: '未开通',
                            attendanceState: '未开通',
                            operation:{
                                type: '幼儿园',
                                action:['查看']
                            }
                        },
                        {
                            name: '红苹果幼儿园',
                            phoneNumber: 13512341234,
                            monitorState: '已开通',
                            attendanceState: '已开通',
                            operation:{
                                type: '幼儿园',
                                action:['查看']
                            }
                        },
                        {
                            name: '红太阳幼儿园',
                            phoneNumber: 13512341234,
                            monitorState: '未开通',
                            attendanceState: '未开通',
                            operation:{
                                type: '幼儿园',
                                action:['查看']
                            }
                        }
                    ]
                }
            }
        },
        methods:{
            closeDetail:function () {
                this.showDetail = '';
            },
            operation:function (type,index,value) {
                switch(type){
                    case '家长':
                        switch (value){
                            case '查看':
                                this.showDetail = 'patriarch';
                                break;
                        }
                        break;
                    case '宝宝':
                        switch (value){
                            case '查看':
                                this.showDetail = 'baby';
                                break;
                        }
                        break;
                    case '老师':
                        switch (value){
                            case '查看':
                                this.showDetail = 'teacher';
                                break;
                        }
                        break;
                    case '幼儿园':
                        switch (value){
                            case '查看':
                                this.showDetail = 'kindergarten';
                                break;
                        }
                        break;

                }
                console.log("type="+type +" index="+index+" value="+value);
            },
            getGoPage:Vue.prototype.$getGoPage
        },
        computed:{
            current_usersData:function () {
                switch(this.current_usersType){
                    case '家长信息管理':
                        return JSON.parse(JSON.stringify(this.parents));
                    case '宝宝信息管理':
                        return JSON.parse(JSON.stringify(this.babys));
                    case '老师信息管理':
                        return JSON.parse(JSON.stringify(this.teachers));
                    case '幼儿园信息管理':
                        return JSON.parse(JSON.stringify(this.kindergartens));
                }
            },
            getData:function () {
                switch(this.current_usersType){
                    case '家长信息管理':
                        return this.current_usersType+address3.current_province + address3.current_city + address3.current_counties+kindergartens.current_kindergarten_id + this.name + this.phoneNumber + this.monitor + this.attendance +this.pageNo
                }
            }
        },
        watch:{
            getData:function () {
                console.log(this.getData);
            }
        },
        created:function () {
            this.current_usersType = '家长信息管理';
        }
    });
    // 路由-幼儿园管理
    var kindergartenManage = Vue.component('kindergarten-manage',{
        template:
        '<div class="kindergartenManage">'+
             '<div class="nav" @click="showTab($event)">'+
                 '<div :class="{current:show_current === '+"'幼儿园列表'"+'}">幼儿园列表</div>'+
                 '<div :class="{current:show_current === '+"'幼儿园添加'"+'}">幼儿园添加</div>'+
             '</div>'+
             '<div class="search" v-show="show_current === '+"'幼儿园列表'"+'">'+
                '<div class="filter">' +
                    '名称:&nbsp&nbsp<input class="name" type="text">'+
                    '手机:&nbsp&nbsp<input class="phoneNumber" type="text">'+
                    '<public-address3></public-address3>'+
                '</div>'+
                '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" :type="tableType"></public-table>' +
                '<public-paging></public-paging>'+
             '</div>'+
            '<div class="add-Newdata" v-show="show_current === '+"'幼儿园添加'"+'">' +
                '<h3>幼儿园添加</h3>'+
                '<div>幼儿园名: <input type="text"></div>'+
                '<div>组织编码索引: <input type="text" value=""></div>'+
                '<div>省市区: <public-address3></public-address3></div>'+
                '<div>详细地址: <input type="text"></div>'+
                '<div>冻结状态:&nbsp'+
                '<select name="" id="">'+
                    '<option value="">正常</option>'+
                    '<option value="">冻结</option>'+
                '</select>'+
                '</div>'+
                '<div>联系人:&nbsp&nbsp&nbsp <input type="text"></div>'+
                '<div>手机号:&nbsp&nbsp&nbsp <input type="text"></div>'+
                '<div>幼儿园归属人联系方式: <input type="text"></div>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
             '<div class="look-Detaildata" v-show="show_kindergarten_detail">'+
                 '<h3>幼儿园详细信息</h3>'+
                 '<div>幼儿园名: <input type="text" value="xxx幼儿园" disabled></div>'+
                 '<div>幼儿园ID: <input type="text" value="#123456" disabled></div>'+
                 '<div>签约人: <input type="text" value="" disabled></div>'+
                 '<div>幼儿园归属人联系方式: <input type="text" disabled></div>'+
                 '<div>合同编号: <input type="text" value="" disabled></div>'+
                 '<div>合同起始日期: <input type="text" value="" disabled></div>'+
                 '<div>合同截止日期: <input type="text" value="" disabled></div>'+
                 '<div>组织编码索引: <input type="text" value=""disabled></div>'+
                 '<div>注册时间: <input type="text" disabled></div>'+
                 '<div>省份: <input type="text" disabled></div>'+
                 '<div>城市: <input type="text" disabled></div>'+
                 '<div>县区: <input type="text" disabled></div>'+
                 '<div>详细地址: <input type="text" disabled></div>'+
                 '<div>冻结状态: <input type="text" disabled></div>'+
                 '<div>手机号: <input type="text" disabled></div>'+
                 '<div>幼儿园收费标准: <input type="text" disabled></div>'+
                 '<div>幼儿园视频开通状况: <input type="text" disabled></div>'+
                 '<div>幼儿园考勤开通状况: <input type="text" disabled></div>'+
                 '<div class="look"><input @click="clear_kindergarten_detail" class="clear" type="button" value="关闭"></div>'+
             '</div>'+
        '</div>',
        data:function () {
            return {
                show_current: '幼儿园列表', //true 显示幼儿园列表 false 显示幼儿园添加
                show_kindergarten_detail:true, //是否显示幼儿园详细信息
                tableType:'kindergartenManage',
                tableTitle: ['幼儿园', '合同编号','联系人','手机', '城市', '注册时间', '组织编码索引', '操作'],
                tableItem: [//假数据
                    {
                        kindergarten: '红太阳幼儿园',
                        contractNo: 'ZL－20170714－32',
                        contactPerson: '张某某',
                        phoneNumber: 16812341234,
                        city: '温州',
                        addTime: '2017/07/08',
                        organizationCode: 3406401005000000002,
                    },
                    {
                        kindergarten: '红太阳幼儿园',
                        contractNo: 'ZL－20170714－32',
                        contactPerson: '张某某',
                        phoneNumber: 16812341234,
                        city: '温州',
                        addTime: '2017/07/08',
                        organizationCode: 3406401005000000002,
                    }
                ]
            }
        },
        methods:{
            showTab:function (e) {
                var target = e.target.innerHTML;
                switch(target){
                    case '幼儿园列表':
                        this.show_current = '幼儿园列表';
                        break;
                    case '幼儿园添加':
                        this.show_current = '幼儿园添加';
                        break;
                }
            },
            clear_kindergarten_detail:function () {
                this.show_kindergarten_detail = false;
            }
        }
    });
    //推送消息
    var pushInfo = Vue.component('info-push',{
       template:
            '<div class="pushInfo">' +
                '<div class="filter">' +
                    '<public-gradeAndClass></public-gradeAndClass>'+
                    '<select>' +
                        '<option>全部</option>'+
                        '<option>家长</option>'+
                        '<option>老师</option>'+
                    '</select>'+
                '</div>'+
                '<div class="pushInfo-info">' +
                    '<div><span>主题</span><input type="text" class="pushInfo-info-title" placeholder="标题"></div>'+
                    '<div class="pushInfo-info-main"><span>正文</span><textarea placeholder="您要推送的消息"></textarea></div>'+
                    '<input class="btn" type="button" value="推送消息">'+
                '</div>'+
            '</div>'
    });
    //消息历史
    var infoHistory = Vue.component('info-history',{
       template:
       '<div class="infoHistory">' +
            '<div class="filter">' +
                '<public-date></public-date>'+
                '类型: '+
                '<select>' +
                    '<option>全部</option>'+
                    '<option>家长</option>'+
                    '<option>老师</option>'+
                '</select>'+
            '</div>'+
            '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" v-on:getOperationObj="operation"></public-table>'+
           '<public-paging></public-paging>'+
            '<div class="look-Detaildata" v-show="showInfo">'+
                '<h3>通知详情: </h3>'+
                '<textarea disabled>{{tableItem[current_index].operation.content}}</textarea>'+
                '<div class="look"><input @click="showInfo=false" class="clear" type="button" value="关闭"></div>'+
            '</div>'+
       '</div>',
        data:function () {
            return {
                current_index:0,
                showInfo: false,
                tableTitle:['通知发送时间','通知发送对象','隐藏','通知发送内容'],
                tableItem:[
                    {
                        time: '2017/07/19',
                        type: '家长',
                        operation: {
                            action:['查看详情'],
                            content: '我是发送给家长的消息我是发送给家长的消息我是发送给家长的消息我是发送给家长的消息我是发送给家长的消息我是发送给家长的消息'
                        }
                    },
                    {
                        time: '2017/07/19',
                        type: '老师',
                        operation: {
                            action:['查看详情'],
                            content:'我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息我是发送给老师的信息'
                        }
                    }
                ]
            }
        },
        methods:{
           operation:function (type,index,value) {
               this.showInfo = true;
               this.current_index = index;
               console.log(typeof this.current_index);
           }
        }
    });
    //使用者管理
    var adminManage = Vue.component('admin-manage',{
       template:
       '<div class="adminManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'管理员列表'"+'}">管理员列表</div>'+
                '<div :class="{current:show_current === '+"'管理员添加'"+'}">管理员添加</div>'+
            '</div>'+
            '<div class="admin-list" v-show="show_current === '+"'管理员列表'"+'">' +
                '<h3>管理员列表: </h3>'+
                '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" v-on:getOperationObj="operation"></public-table>'+
                '<public-paging></public-paging>'+
                '<div class="look-Detaildata" v-show="recompose_admin">' +
                    '<h3>查看或修改管理员信息</h3>'+
                    '<div>注册时间: <input type="text" disabled></div>'+
                    '<div>姓名: <input type="text"></div>'+
                    '<div>职称: <input type="text"></div>'+
                    '<div>手机号: <input type="text"></div>'+
                    '<div>密码: <input type="text"></div>'+
                    '<div>注册时间: <input type="text" disabled></div>'+
                    '<div class="postData"><input class="save" type="button" value="保存">&nbsp&nbsp&nbsp&nbsp<input @click="recompose_admin=false" class="clear" type="button" value="取消"></div>'+
                '</div>'+
            '</div>'+
            '<div class="add-Newdata" v-show="show_current === '+"'管理员添加'"+'">' +
                '<h3>管理员添加</h3>'+
                '<div>姓名*: <input type="text"></div>'+
                '<div>手机号*: <input type="text"></div>'+
                '<div>职称: <input type="text"></div>'+
                '<p class="mark">注: *为必填项 账号名默认为手机号 密码默认为123456</p>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
       '</div>',
        data:function () {
          return {
              show_current: '管理员列表',
              recompose_admin:false,
              tableTitle: ['姓名','手机号','注册时间','隐藏','操作'],
              tableItem:[
                  {
                      name: '张三',
                      phoneNumber: 13812345678,
                      registerTime: '2017/07/20',
                      operation:{
                          action:['修改','删除',]
                      }
                  },
                  {
                      name: '李四',
                      phoneNumber: 13812345678,
                      registerTime: '2017/07/20',
                      operation:{
                          action:['修改','删除']
                      }
                  }
              ]
          }
        },
        methods:{
            showTab:function (e) {
                this.show_current = e.target.innerHTML;
            },
            operation:function (type,index,value) {
                switch(value){
                    case '修改':
                        this.recompose_admin = true;
                        break;
                }
            }
        }
    });
    //老师管理
    var teacherManage = Vue.component('teacher-manage',{
        template:
        '<div class="teacherManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'老师列表'"+'}">老师列表</div>'+
                '<div :class="{current:show_current === '+"'老师添加'"+'}">老师添加</div>'+
            '</div>'+
            '<div class="teacher-list" v-show="show_current === '+"'老师列表'"+'">' +
                '<div class="filter">'+
                    '<div class="inlineBlock">姓名: <input class="name" type="text"></div>'+
                    '<div class="inlineBlock">手机号: <input class="phoneNumber" type="text"></div>'+
                '</div>'+
                '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" v-on:getOperationObj="operation"></public-table>'+
                '<public-paging></public-paging>'+
                '<div class="look-Detaildata" v-show="recompose_teacher">' +
                    '<h3>老师信息修改: </h3>'+
                    '<div>注册时间: <input type="text" value="" ></div>'+
                    '<div>姓名: <input type="text" value="" ></div>'+
                    '<div>性别: <input type="text" value="" ></div>'+
                    '<div>年龄: <input type="text" value=""></div>'+
                    '<div>学历: <input type="text" value="" ></div>'+
                    '<div>教师资格证: <input type="text" value="" ></div>'+
                    '<div>普通话: <input type="text" value="" ></div>'+
                    '<div>考勤卡卡号: <input  type="text" value="" ></div>'+
                    '<div>教职工编号: <input  type="text" value="" ></div>'+
                    '<div>所带班级: <input  type="text" value="" ></div>'+
                    '<div>手机号: <input  type="text" value="" ></div>'+
                    '<div>密码: <input  type="text" value="" ></div>'+
                    '<div class="postData"><input class="save" type="button" value="保存">&nbsp&nbsp&nbsp&nbsp<input @click="recompose_teacher=false" class="clear" type="button" value="取消"></div>'+
                '</div>'+
            '</div>'+
            '<div class="add-Newdata" v-show="show_current === '+"'老师添加'"+'">' +
                '<h3>老师添加</h3>'+
                '<div>姓名*: <input type="text" value="" ></div>'+
                '<div>性别*: <input type="text" value="" ></div>'+
                '<div>年龄*: <input type="text" value=""></div>'+
                '<div>手机号*: <input  type="text" value="" ></div>'+
                '<div>所带班级*: <input  type="text" value="" ></div>'+
                '<div>学历: <input type="text" value="" ></div>'+
                '<div>教师资格证: <input type="text" value="" ></div>'+
                '<div>普通话: <input type="text" value="" ></div>'+
                '<p class="mark">注:*为必填项  账号名默认为手机号 密码默认为123456 </p>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
        '</div>',
        data:function () {
            return {
                show_current: '老师列表',
                recompose_teacher:false,
                tableTitle:['姓名', '性别', '年龄', '手机','所带班级','隐藏', '操作'],
                tableItem:[
                    {
                        name: '小乔',
                        sex: '女',
                        age: '23',
                        phoneNumber: 13912345678,
                        nowClass: '大班A班',
                        operation:{
                            type: '老师',
                            action:['查看或修改','删除']
                        }
                    },
                    {
                        name: '甄氏',
                        sex: '女',
                        age: '22',
                        phoneNumber: 13912345678,
                        nowClass: '大班A班',
                        operation:{
                            type: '老师',
                            action:['查看或修改','删除']
                        }
                    },
                    {
                        name: '大乔',
                        sex: '女',
                        age: '24',
                        phoneNumber: 13912345678,
                        nowClass: '大班A班',
                        operation:{
                            type: '老师',
                            action:['查看或修改','删除']
                        }
                    },
                    {
                        name: '貂蝉',
                        sex: '女',
                        age: '23',
                        phoneNumber: 13912345678,
                        nowClass: '大班A班',
                        operation:{
                            type: '老师',
                            action:['查看或修改','删除']
                        }
                    }
                ]
            }
        },
        methods:{
            showTab:function (e) {
                this.show_current = e.target.innerHTML;
            },
            operation:function (type,index,value) {
                switch(value){
                    case '查看或修改':
                        this.recompose_teacher = true;
                        break;
                }
            }
        }
    });
    //宝宝管理
    var babyManage = Vue.component('baby-manage',{
        template:
        '<div class="babyManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'宝宝列表'"+'}">宝宝列表</div>'+
                '<div :class="{current:show_current === '+"'宝宝添加'"+'}">宝宝添加</div>'+
            '</div>'+
            '<div class="baby-list" v-show="show_current === '+"'宝宝列表'"+'">' +
                '<div class="filter">'+
                    '姓名: <input class="name" type="text">'+
                    '<public-gradeAndClass></public-gradeAndClass>'+
                '</div>'+
                '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" v-on:getOperationObj="operation"></public-table>'+
                '<public-paging></public-paging>'+
                '<div class="look-Detaildata" v-show="recompose_baby">' +
                    '<h3>修改宝宝信息</h3>'+
                    '<div>注册时间: <input type="text" value="" disabled></div>'+
                    '<div>姓名: <input type="text" value=""></div>'+
                    '<div>性别: <input type="text" value=""></div>'+
                    '<div>年龄: <input type="text" value=""></div>'+
                    '<div>ID: <input type="text" value="" disabled></div>'+
                    '<div>考勤卡卡号: <input  type="text" value="" disabled></div>'+
                    '<div>带班老师: <input  type="text" value=""></div>'+
                    '<div>年级: <input type="text"></div>'+
                    '<div>班级: <input type="text"></div>'+
                    '<div>监护人: <input type="text"></div>'+
                    '<div>监护人关系: <input type="text"></div>'+
                    '<div>监护人手机号: <input type="text"></div>'+
                    '<div class="postData"><input class="save" type="button" value="保存">&nbsp&nbsp&nbsp&nbsp<input @click="recompose_baby=false" class="clear" type="button" value="取消"></div>'+
                '</div>'+
            '</div>'+
            '<div class="add-Newdata" v-show="show_current === '+"'宝宝添加'"+'">' +
                '<h3>宝宝添加(确保已录入该宝宝的家长信息)</h3>'+
                '<div>姓名*: <input type="text"></div>'+
                '<div>性别*: <input type="text"></div>'+
                '<div>出生日期*: <input name="birth" type="text">年<input name="birth" type="text">月<input name="birth" type="text">日</div>'+
                '<div>监护人*: <input type="text"></div>'+
                '<div>监护人关系*: <input type="text"></div>'+
                '<div>监护人手机号*: <input type="text"></div>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
        '</div>',
        data:function () {
            return {
                show_current: '宝宝列表',
                recompose_baby:false,
                tableTitle: ['姓名', '性别','年级', '班级','隐藏', '操作'],
                tableItem: [ //假数据
                    {
                        name: '李白',
                        sex: '男',
                        grade: '中班',
                        class: '1班',
                        operation:{
                            type: '宝宝',
                            action:['查看或修改','删除']
                        }

                    },
                    {
                        name: '杜甫',
                        sex: '男',
                        grade: '大班',
                        class: '2班',
                        operation:{
                            type: '宝宝',
                            action:['查看或修改','删除']
                        }
                    },
                    {
                        name: '李清照',
                        sex: '女',
                        grade: '小班',
                        class: '3班',
                        operation:{
                            type: '宝宝',
                            action:['查看或修改','删除']
                        }
                    }
                ],
            }
        },
        methods:{
            showTab:function (e) {
                this.show_current = e.target.innerHTML;
            },
            operation:function (type,index,value) {
                switch(value){
                    case '查看或修改':
                        this.recompose_baby = true;
                        break;
                }
            }
        }
    });
    //家长管理
    var patriarchManage = Vue.component('patriarch',{
        template:
        '<div class="patriarchManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'家长列表'"+'}">家长列表</div>'+
                '<div :class="{current:show_current === '+"'家长添加'"+'}">家长添加</div>'+
            '</div>'+
            '<div class="patriarch-list" v-show="show_current === '+"'家长列表'"+'">' +
                '<div class="filter">'+
                    '姓名: <input class="name" type="text">'+
                    '手机号: <input class="phoneNumber" type="text">'+
                    '视频功能: '+
                    '<select>'+
                        '<option>请选择</option>'+
                        '<option>未开通</option>'+
                        '<option>已开通</option>'+
                    '</select>'+
                    '考勤功能: '+
                    '<select>'+
                        '<option>请选择</option>'+
                        '<option>未开通</option>'+
                        '<option>已开通</option>'+
                    '</select>'+
                '</div>'+
                '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length" v-on:getOperationObj="operation"></public-table>'+
                '<public-paging></public-paging>'+
                '<div class="look-Detaildata" v-show="recompose_patriarch">' +
                    '<h3>修改家长信息: </h3>'+
                    '<div>姓名: <input type="text" value="" ></div>'+
                    '<div>详细地址: <input type="text" value="" </div>'+
                    '<div>手机号: <input type="text" value=""></div>'+
                    '<div>密码: <input type="text"></div>'+
                    '<div>视频到期时间: <input type="text" disabled></div>'+
                    '<div>考勤到期时间: <input type="text" disabled></div>'+
                    '<h3>宝宝信息: </h3>'+
                    '<public-table :title="tableTitle_babys" :item="tableItem_babys"></public-table>'+
                    '<div class="postData"><input class="save" type="button" value="保存">&nbsp&nbsp&nbsp&nbsp<input @click="recompose_patriarch=false" class="clear" type="button" value="取消"></div>'+
                '</div>'+
            '</div>'+
            '<div class="add-Newdata" v-show="show_current === '+"'家长添加'"+'">' +
                '<h3>家长添加</h3>'+
                '<div>姓名*: <input type="text"></div>'+
                '<div>省市区*: <public-address3></public-address3></div>'+
                '<div>详细地址*: <input type="text"></div>'+
                '<div>手机号*: <input type="text"></div>'+
                '<p class="mark">注: *为必填项 账号名默认为手机号 密码默认为123456</p>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
        '</div>',
        data:function () {
            return {
                show_current: '家长列表',
                recompose_patriarch:false,
                tableTitle: ['姓名', '手机号', '视频功能', '考勤功能','隐藏','操作'],
                tableItem: [ //假数据
                    {
                        name: '张3',
                        phoneNumber: 13912341234,
                        monitorState: '未开通',
                        attendanceState: '未开通',
                        operation:{
                            type: '家长',
                            action:['查看或修改','删除']
                        }
                    },
                    {
                        name: '张4',
                        phoneNumber: 13912341234,
                        monitorState: '未开通',
                        attendanceState: '未开通',
                        operation:{
                            type: '家长',
                            action:['查看或修改','删除']
                        }
                    }
                ],
                tableTitle_babys:['姓名', '性别', '年级', '班级'],
                tableItem_babys:[
                    {
                        name: '李白',
                        sex: '男',
                        grade: '中班',
                        class: '1班',
                    },
                    {
                        name: '杜甫',
                        sex: '男',
                        grade: '大班',
                        class: '2班',
                    }
                ]
            }
        },
        methods:{
            showTab:function (e) {
                this.show_current = e.target.innerHTML;
            },
            operation:function (type,index,value) {
                switch(value){
                    case '查看或修改':
                        this.recompose_patriarch = true;
                        break;
                }
            }
        }
    });
    // 年级、班级管理
    var gradeAndClassManage = Vue.component('gradeAndClass-Manage',{
       template:
       '<div class="gradeAndClassManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'班级信息'"+'}">班级信息</div>'+
                '<div :class="{current:show_current === '+"'添加年级、班级'"+'}">添加年级、班级</div>'+
            '</div>'+
            '<div class="classInfo" v-show="show_current==='+"'班级信息'"+'">' +
                '选择年级: <public-gradeAndClass :hideClass="true"></public-gradeAndClass>'+
                '<public-table :title="classInfo.tableTitle" :item="classInfo.tableItem" :itemCount="16-classInfo.tableItem.length"></public-table>'+
                '<public-paging></public-paging>'+
            '</div>'+
            '<div class="addGradeAndClass add-Newdata" v-show="show_current==='+"'添加年级、班级'"+'">' +
                '<h3>添加年级、班级</h3>'+
                '选择您要添加的年级或班级: '+
                '<select v-model="addGradeAndClass.add_type" style="color:#000">' +
                    '<option>年级</option>'+
                    '<option>班级</option>'+
                '</select>'+
                '<div class="addGrade" v-show="addGradeAndClass.add_type==='+"'年级'"+'">' +
                    '请输入年级名字: <input type="text">'+
                '</div>'+
                '<div class="addClass" v-show="addGradeAndClass.add_type==='+"'班级'"+'">' +
                    '选择该班级所对应的年级: <public-gradeAndClass :hideClass="true"></public-gradeAndClass >'+
                    '请输入班级名字: <input type="text">'+
                '</div>'+
                '<div class="postData"><input class="save" type="button" value="保存"></div>'+
            '</div>'+
       '</div>',
        data:function () {
            return {
                show_current: '添加年级、班级',
                classInfo:{
                    tableTitle:['班级名字','带班老师','学生数量','隐藏','操作'],
                    tableItem:[
                        {
                            name: '1班',
                            leaderTeacher_name:'洛可可',
                            students_count:50,
                            operation:{
                                action:['修改','删除'],
                            }
                        }
                    ]
                },
                addGradeAndClass:{
                    add_type: '年级', //用于是否显示班级
                },

            }
        },
        methods:{
           showTab:function (e) {
               this.show_current = e.target.innerHTML;
               console.log(this.show_current);
           }
        }
    });
    //毕业管理
    var graduateManage = Vue.component('graduate-manage',{
        template:
        '<div class="graduateManage">' +
            '<div class="filter">' +
                '<public-gradeAndClass></public-gradeAndClass>'+
            '</div>'+
            '<input class="save" type="button" value="毕业">'+
        '</div>'
    });
    //升班管理
    var upGrade = Vue.component('up-grade',{
        template:
        '<div class="upGrade">' +
            '<div class="filter">' +
                '原先班级: '+
                '<public-gradeAndClass></public-gradeAndClass>'+
            '</div>'+
            '<div class="nowClass">'+
                '升级后的班级: '+
                '<public-gradeAndClass></public-gradeAndClass>'+
                '<input class="save" type="button" value="升班">'+
            '</div>'+
        '</div>'
    });
    //考勤时间管理
    var attendanceTimeManage = Vue.component('attendanceTime-manage',{
        template:
        '<div class="attendanceTimeManage">' +
            '<div class="checkIn">' +
                '上午打卡时间段: '+
                '<input type=text :disabled="alter" name="checkIn">~<input type=text :disabled="alter" name="checkIn">'+
                '下午打卡时间段: '+
                '<input type=text :disabled="alter" name="checkIn">~<input type=text :disabled="alter" name="checkIn">'+
                '<input type="button" value="修改" class="save" v-show="alter===true" @click="alter=false">'+
                '<input type="button" value="提交" class="save" v-show="alter===false">'+
            '</div>'+
            '<div>添加打卡日期: <public-date></public-date> <input type="button" value="添加" class="save"></div>'+
            '<div>删除打卡日期: <public-date></public-date> <input type="button" value="删除" class="save"></div>'+
            '<div class="mark">注: 周一至周五默认需打卡(周六、周日默认无需打卡)如需修改请选择相应日期添加或删除</div>'+
            '<h3>当前额外添加、删除的打卡日: </h3>'+
            '<public-table :title="addCheckTime.tableTitle"  :item="addCheckTime.tableItem"></public-table>'+
            '<public-table :title="deleteCheckTime.tableTitle"  :item="deleteCheckTime.tableItem"></public-table>'+
        '</div>',
        data:function () {
            return {
                alter: true,
                addCheckTime:{
                    tableTitle:['工作日无需打卡的日期'],
                    tableItem:[
                        {
                            time: '2017/07/30'
                        },
                        {
                            time: '2017/07/31'
                        }
                    ],
                },
                deleteCheckTime:{
                    tableTitle:['周末需打卡的日期'],
                    tableItem:[
                        {
                            time: '2017/07/30'
                        },
                        {
                            time: '2017/07/31'
                        }
                    ],
                }
            }
        }
    });
    //考勤卡管理
    var attendanceCardManage = Vue.component('attendanceCard-manage',{
        template:
        '<div class="attendanceCardManage">' +
            '<div class="nav" @click="showTab($event)">'+
                '<div :class="{current:show_current === '+"'考勤卡绑定'"+'}">考勤卡绑定</div>'+
                '<div :class="{current:show_current === '+"'考勤卡补领'"+'}">考勤卡补领</div>'+
            '</div>'+
            '<div class="attendance_bind" v-show="show_current==='+"'考勤卡绑定'"+'">' +
                '<div class="filter">' +
                    '<select>' +
                        '<option>类型</option>'+
                        '<option>宝宝</option>'+
                        '<option>老师</option>'+
                        '<option>园长</option>'+
                        '<option>门卫</option>'+
                    '</select>'+
                    '<select>' +
                        '<option>考勤卡绑定状态</option>'+
                        '<option>已绑定</option>'+
                        '<option>未绑定</option>'+
                    '</select>'+
                '</div>'+
                '<public-table :title="attendanceCard_bind.tableTitle" :item="attendanceCard_bind.tableItem" :itemCount="attendanceCard_bind.tableItem.length"></public-table>'+
                '<public-paging></public-paging>'+
            '</div>'+
            '<div class="attendance_look" v-show="show_current==='+"'考勤卡补领'"+'">' +
                '<select>' +
                    '<option>全部</option>'+
                    '<option>宝宝</option>'+
                    '<option>老师</option>'+
                    '<option>园长</option>'+
                    '<option>门卫</option>'+
                '</select>'+
                '<div>考勤卡(卡号): <input type="text"></div>'+
                '<public-table :title="attendanceCard_look.tableTitle" :item="attendanceCard_look.tableItem" :itemCount="16-attendanceCard_look.tableItem.length"></public-table>'+
                '<public-paging></public-paging>'+
            '</div>'+
        '</div>',
        data:function () {
            return{
                show_current:'考勤卡绑定',
                pages:6,
                pageNo:1,
                attendanceCard_bind:{
                    tableTitle:['类型','姓名','id','考勤卡ID','隐藏','操作'],
                    tableItem:[
                        {
                            type: '老师',
                            name: '张向西',
                            id: '#456',
                            attendanceCard_id: 123456,
                            operation:{
                                type: '考勤卡',
                                action: ['绑定']
                            }
                        }
                    ]
                },
                attendanceCard_look:{
                    tableTitle:['考勤卡卡号','类型','持卡人姓名','隐藏','操作'],
                    tableItem:[
                        {
                            attendanceCardID: 123456789,
                            type: '园长',
                            cardholder_name: '高仓文太',
                            operation:{
                                type: '考勤卡',
                                action: ['解绑','补领']
                            }
                        },
                        {
                            attendanceCardID: 123456789,
                            type: '宝宝',
                            cardholder_name: '野原新之助',
                            operation:{
                                type: '考勤卡',
                                action: ['解绑','补领']
                            }
                        },
                        {
                            attendanceCardID: 123456789,
                            type: '老师',
                            cardholder_name: '吉永绿',
                            operation:{
                                type: '考勤卡',
                                action: ['解绑','补领']
                            }
                        },

                    ]
                }
            }
        },
        methods:{
            showTab:function (e) {
                this.show_current = e.target.innerHTML;

            }
        }
    });
    //考勤异常
    var attendanceAnomalyManage = Vue.component('attendance-anomaly',{
        template:
        '<div class="attendanceAnomalyManage">' +
            '<div class="filter">' +
                '<public-date></public-date>'+
                '<select>' +
                    '<option>类型</option>'+
                    '<option>宝宝</option>'+
                    '<option>老师</option>'+
                    '<option>园长</option>'+
                    '<option>门卫</option>'+
                '</select>'+
                '<select>' +
                    '<option>是否处理</option>'+
                    '<option>已处理</option>'+
                    '<option>未处理</option>'+
                '</select>'+
            '</div>'+
            '<public-table :title="tableTitle" :item="tableItem"></public-table>'+
        '</div>',
        data:function () {
            return {
                tableTitle: ['姓名','类型','异常时间','异常类型','是否处理','隐藏','操作'],
                tableItem:[
                    {
                        name: '小白',
                        type: '宝宝',
                        anomalyTime: '2017/07/22 14:20',
                        anomalyType: '早退',
                        state: '已处理',
                        operation:{
                            action: ['处理异常']
                        }
                    },
                    {
                        name: '小白',
                        type: '宝宝',
                        anomalyTime: '2017/07/22 14:20',
                        anomalyType: '早退',
                        state: '未处理',
                        operation:{
                            action: ['处理异常']
                        }
                    }
                ]
            }
        }
    });
    //请假管理
    var leaveManage = Vue.component('leave-manage',{
       template:
       '<div class="leaveManage">' +
            '<div class="filter">' +
                '<public-gradeAndClass></public-gradeAndClass>'+
                '<public-date></public-date>'+
            '</div>'+
       '<public-table :title="tableTitle" :item="tableItem"></public-table>'+
       '</div>',
        data:function () {
            return{
                tableTitle: ['宝宝姓名','请假时间','申请时间','请假理由','假条状态'],
                tableItem:[
                    {
                        name: '张三',
                        leaveTime: '2017/07/22 10:00:00',
                        applyTime: '2017/07/21 12:00:00',
                        leaveReason: '生病',
                        leaveState: '已批准'
                    },
                    {
                        name: '李四',
                        leaveTime: '2017/07/22 10:00:00',
                        applyTime: '2017/07/21 12:00:00',
                        leaveReason: '生病',
                        leaveState: '已批准'
                    }
                ]
            }
        }
    });
    //晨检查看
    var morningCheckLook = Vue.component('morning-look',{
        template:
        '<div class="morningCheckInput">' +
            '<div class="filter">' +
                '<public-date></public-date>' +
                '<public-gradeAndClass></public-gradeAndClass>' +
                '<input type="button" class="save" value="将晨检信息发送给老师">'+
            '</div>'+
            '<public-table :title="tableTitle" :item="tableItem" :itemCount="16-tableItem.length"></public-table>'+
            '<public-paging></public-paging>'+
        '</div>',
        data:function () {
            return {
                tableTitle:['日期','年级','班级','宝宝姓名','体温'],
                tableItem:[
                    {
                        date: '2017/07/25',
                        grade: '大班',
                        class: '1班',
                        name: '大宝',
                        heat: 36.5,
                    },
                    {
                        date: '2017/07/25',
                        grade: '大班',
                        class: '1班',
                        name: '小宝',
                        heat: 36.6,
                    }
                ]
            }
        }
    });
    // 课程管理 一周固定
    // var curriculumManage = Vue.component('curriculum-manage',{
    //     template:
    //     '<div class="curriculumManage">' +
    //     '<div class="filter">' +
    //     '<public-gradeAndClass></public-gradeAndClass>'+
    //     '</div>'+
    //     '<h3>课程表: </h3>'+
    //         '<div @click="addDleteCourse($event)">' +
    //             '<input type="button" class="btn-skyblue" value="添加上午课程"> ' +
    //             '<input type="button" class="btn-skyblue" value="添加下午课程"> ' +
    //             '<input type="button" class="btn-skyblue" value="删除上午课程"> ' +
    //             '<input type="button" class="btn-skyblue" value="删除下午课程"> ' +
    //         '</div>'+
    //         '<table>' +
    //             '<thead>' +
    //                 '<tr>' +
    //                     '<th></th>'+
    //                     '<th>时间</th>'+
    //                     '<th v-for="day in tableTItle">{{day}}</th>'+
    //                 '</tr>'+
    //             '</thead>'+
    //             '<tbody>' +
    //                 '<tr v-for="am in tableItem.am">' +
    //                     '<th ref="th_am">上午</th>'+
    //                     '<th class="time"><input v-model="am.startTime" type="text">-<input v-model="am.endTime" type="text"></th>'+
    //                     '<td v-for="(course,index) in am.course">' +
    //                         '<p><input v-model="am.course[index]" type="text"></p>' +
    //                     '</td>'+
    //                 '</tr>'+
    //                 '<tr v-for="pm in tableItem.pm">' +
    //                     '<th ref="th_pm">下午</th>'+
    //                     '<th class="time"><input v-model="pm.startTime" type="text">-<input v-model="pm.endTime" type="text"></th>'+
    //                     '<td v-for="(course,index) in pm.course">' +
    //                         '<p><input v-model="pm.course[index]" type="text"></p>' +
    //                     '</td>'+
    //                 '</tr>'+
    //             '</tbody>'+
    //         '</table>'+
    //     '</div>',
    //     data:function () {
    //         return {
    //             tableTItle: ['周一','周二','周三','周四','周五','周六','周日'],
    //             tableItem:{
    //                 am: [
    //                     {
    //                         startTime: '9:00',
    //                         endTime:'9:45',
    //                         course: ['语文', '数学', '英语', '普通话', '绘画', '', '']
    //                     },
    //                     {
    //                         startTime: '10:00',
    //                         endTime:'10:45',
    //                         course: ['积木', '手偶', '话剧', '故事', '音乐', '', '']
    //                     },
    //                     {
    //                         startTime: '11:00',
    //                         endTime:'11:45',
    //                         course: ['积木', '手偶', '话剧', '故事', '音乐', '', '']
    //                     }
    //                 ],
    //                 pm: [
    //                     {
    //                         startTime: '13:30',
    //                         endTime:'14:15',
    //                         course: ['积木', '手偶', '话剧', '故事', '音乐', '', '']
    //                     },
    //                     {
    //                         startTime: '14:30',
    //                         endTime:'15:15',
    //                         course: ['积木', '', '话剧', '故事', '', '', '']
    //                     }
    //                 ]
    //             },
    //         }
    //     },
    //     methods:{
    //         //添加删除课程
    //         addDleteCourse:function (e) {
    //             var courses = {
    //                 startTime: '',
    //                 endTime:'',
    //                 course: ['', '', '', '', '', '', '']
    //             },
    //                 am = this.tableItem.am,
    //                 pm = this.tableItem.pm;
    //             switch(e.target.value){
    //                 case '添加上午课程':
    //                     am.splice(am.length,0,courses);
    //                     break;
    //                 case '删除上午课程':
    //                     am.splice(am.length-1,1);
    //                     break;
    //                 case '添加下午课程':
    //                     pm.splice(pm.length,0,courses);
    //                     break;
    //                 case '删除下午课程':
    //                     pm.splice(pm.length-1,1);
    //                     break;
    //             }
    //         },
    //         //传入th，将其合并单元格
    //         merge:function (th_arr) {
    //             var i = 0, len = th_arr.length;
    //             if(len === 0){ return }
    //             th_arr[0].rowSpan = len;
    //             for( i = 1; i < len; i++){
    //                 th_arr[i].style.display = 'none';
    //             }
    //         }
    //     },
    //     mounted:function () {
    //         this.merge(this.$refs.th_am);
    //         this.merge(this.$refs.th_pm);
    //     },
    //     updated:function () {
    //         this.merge(this.$refs.th_am);
    //         this.merge(this.$refs.th_pm);
    //     }
    // });
    //食谱设置

    //课程管理
    var curriculumManage = Vue.component('curriculum-manage',{
       template:
       '<div class="curriculumManage">' +
            '<div class="filter">' +
                '<strong>选择日期(必选): </strong> <public-date v-on:giveTimes="getTimes"></public-date>'+
                '<strong>选择年级、班级(必选): </strong><public-gradeAndClass v-on:giveGradeAndClass="getGradeAndClass"></public-gradeAndClass>'+
            '</div>'+
            '<div><input type="button" class="btn-skyblue" value="添加课程" @click="addCourse"></div>'+
            '<table>' +
                '<thead>' +
                    '<tr>' +
                        '<th>日期</th>'+
                        '<th>年级</th>'+
                        '<th>班级</th>'+
                        '<th>上午</th>'+
                        '<th>时间段</th>'+
                        '<th>课程名称</th>'+
                        '<th>操作</th>'+
                    '</tr>'+
                '</thead>'+
                '<tbody>' +
                    '<tr v-for="course in courses">' +
                        '<td>{{date_web}}</td>'+
                        '<td>{{Grade}}</td>'+
                        '<td>{{Class}}</td>'+
                        '<td><input type="text" v-model="course.ampm"></td>'+
                        '<td><input type="text" v-model="course.startTime"> - <input type="text" v-model="course.endTime"></td>'+
                        '<td><input type="text" v-model="course.course"></td>'+
                        '<td><span v-show="course.isNewData">提交</span><span>删除</span></td>'+
                    '</tr>'+
                '</tbody>'+
            '</table>'+
            '<div class="demo">' +
                '<p class="mark">课程填写规范:(每次填写完需点击提交按钮)</p> '+
                '<public-table :title="demoTitle" :item="demoItem"></public-table>'+
            '</div>'+

       '</div>',
        data:function () {
            return {
                show_current: '课程列表',
                date: 0,
                Grade: '',
                Class: '',
                courses:[
                    {
                        date: 0,
                        leadGrade: '',
                        leadClass: '',
                        ampm: '上午',
                        startTime: '',
                        endTime: '',
                        course: '语文',
                    }
                ],
                demoTitle:['日期','年级','班级','上午','时间段','课程名称'],
                demoItem:[
                    {
                        date: '2017/7/7',
                        Grade: '年级',
                        Class: '班级',
                        ampm:'上午',
                        time: '9:00 - 9:45',
                        course: '普通话',
                    }
                ]
            }
        },
        computed:{
            //网页上的日期显示格式
            date_web:function () {
                var date = new Date(this.date*1000);
                return date.toLocaleDateString();
            }
        },
        methods:{
            showTab:function (e) {
               var target = e.target.innerHTML;
               console.log(target);
               if(target !== '课程列表' && target !== '课程添加') return;
               this.show_current = target;
            },
            getTimes:function (timestamp) {
                this.date = timestamp;
            },
            getGradeAndClass:function (Grade,Class) {
                this.Grade = Grade;
                this.Class = Class;
            },
            addCourse:function () {
                if(!this.Grade || this.Grade === '年级'){
                    alert('请选择年级');
                    return;
                }else if (!this.Class || this.Class === '班级'){
                    alert('请选择班级');
                    return;
                }
                var course = {
                    date: this.date,
                    leadGrade: this.Grade,
                    leadClass: this.Class,
                    ampm: '',
                    startTime: '',
                    endTime: '',
                    course: '',
                    isNewData:true
                }
                this.courses.splice(this.courses.length,0,course);
            }
        }
    });
    //食谱设置
    var cookbookSet = Vue.component('cookbook-set',{
        template:
        '<div class="cookbookSet ">' +
            '<public-date></public-date>'+
            '<form action="" class="clearfix">' +
                '<div class="food clearfix" v-for="n in num">' +
                    '<div>食谱名称: <input name="name" type="text"></div>'+
                    '<div>食谱图片: <input name="pic" type="file"></div>'+
                    '<div>食谱描述: <textarea name="content"></textarea></div>'+
                '</div>'+
            '</form>'+
            '<div class="operation"><input @click="num+=1" class="save" type="button" value="继续添加"><input @click="num-=1" class="save" type="button" value="删除食谱"><input class="save" type="submit"></div>'+
        '</div>',
        data:function () {
            return {
                num : 1,
            }
        }
    });
    //食谱列表
    var cookbookList = Vue.component('cookbook-list',{
       template:
       '<div class="cookbookList">' +
            '<div class="filter">' +
                '<public-date></public-date>'+
            '</div>'+
            '<public-table :title="tableTitle" :item="tableItem"></public-table>'+
            '<public-paging></public-paging>'+
       '</div>',
        data:function () {
            return {
                tableTitle: ['日期','食谱名称','食谱介绍'],
                tableItem:[
                    {
                        date: '2017/07/25',
                        name: '炸鸡翅',
                        introduce: 'junkfood,最好不要给小孩吃'
                    },
                    {
                        date: '2017/07/25',
                        name: '炸鸡翅',
                        introduce: 'junkfood,最好不要给小孩吃'
                    },
                    {
                        date: '2017/07/25',
                        name: '炸鸡翅',
                        introduce: 'junkfood'
                    }
                ]
            }
        }
    });
    //付费查询
    var payQuery = Vue.component('pay-query',{
        template:
        '<div class="payQuery">' +
        '<div class="filter">' +
        '<public-gradeAndClass></public-gradeAndClass>'+
            '<select v-model="type">' +
                '<option>视频付费详情</option>'+
                '<option>考勤付费详情</option>'+
            '</select>'+
        '</div>'+
        '<public-table :title="current.tableTitle" :item="current.tableItem" :itemCount="16-current.tableItem.length"></public-table>'+
            '<public-paging></public-paging>'+
        '</div>',
        data:function () {
            return {
                type: '视频付费详情',
                monitoring:{
                    tableTitle:['家长姓名','视频开始时间','视频到期时间','视频付费详情','家长手机'],
                    tableItem:[
                        {
                            name: '张三',
                            monitoringStartTIme: '2017/07/01',
                            monitoringEndTime: '2017/10/01',
                            monitoringPayDetail:200,
                            phoneNumber: 13512345678
                        },
                        {
                            name: '李四',
                            monitoringStartTIme: '2017/07/01',
                            monitoringEndTime: '2017/10/01',
                            monitoringPayDetail:200,
                            phoneNumber: 13512345678
                        }
                    ]
                },
                attendance:{
                    tableTitle:['宝宝姓名','考勤开始时间','考勤到期时间','考勤付费详情'],
                    tableItem:[
                        {
                            name: '张三的宝宝',
                            attendanceStartTIme: '2017/07/01',
                            attendanceEndTime: '2017/10/01',
                            attendancePayDetail:100,
                        },
                        {
                            name: '李四的宝宝',
                            attendanceStartTIme: '2017/07/01',
                            attendanceEndTime: '2017/10/01',
                            attendancePayDetail:100,
                        }
                    ]
                },

            }
        },
        computed:{
            current: function () {
                switch(this.type){
                    case '视频付费详情':
                        return this.monitoring;
                        break;
                    case '考勤付费详情':
                        return this.attendance;
                        break;
                }
            }
        }
    });
    //全园缴费
    var payFees = Vue.component('pay-fees',{
       template:
        '<div class="payFees">' +
            '<h3 style="color:#006105">全园缴费: </h3>'+
            '<div class="filter">' +
                '选择类型: '+
                '<select v-model="pay_data.type">' +
                    '<option :value="0">幼儿园开通视频</option>'+
                    '<option :value="1">幼儿园开通考勤</option>'+
                    '<option :value="2">幼儿园帮助所有家长开通视频</option>'+
                    '<option :value="3">幼儿园帮助所有宝宝开通考勤</option>'+
                '</select>'+
                '选择时长: '+
                '<select v-model="pay_data.monthCount">' +
                    '<option v-for="n in 12">{{n+'+"'个月'"+'}}</option>'+
                '</select>'+
            '</div>'+
            '<div class="add-Newdata">' +
                '<div>当前幼儿园宝宝人数: <input type="text" disabled></div>'+
                '<div>应缴费价格: <input type="text" disabled></div>'+
                '<input  @click="pay" type="button" value="支付宝支付">'+
            '</div>'+
        '</div>',
        data: function () {
            return {
                pay_data:{
                    token: '255c84f9-adea-4972-b346-84a007431384',
                    type: 0,
                    monthCount: 1,
                    gartenId: 1,
                }
            }
        },
        //http://a.yiyunwangl.com:8082/bigcontrol/alipay.do?
        computed: {
            xhr: function () {
                var self = this;
                return {
                    url: 'http://a.yiyunwangl.com:8082/bigcontrol/alipay.do?',
                    data: 'token=255c84f9-adea-4972-b346-84a007431384&type=0&monthCount=1&gartenId=1',
                    type: 'post',
                    success: function (data) {
                        console.log(1);
                        console.log(data);
                        window.location.href = 'http://a.yiyunwangl.com:8082/bigcontrol/alipay.do?token=255c84f9-adea-4972-b346-84a007431384&type=0&monthCount=1&gartenId=1';
                    }
                }
            }
        },
        methods: {
           pay: function () {
               $.ajax(this.xhr);
           }
        },
        beforeMount: function () {
            $.ajax(this.xhr);
        }
    });

    var routes = [
        //消息推送
        {path: '/pushInfo', component: pushInfo},
        //消息历史
        {path: '/infoHistory', component: infoHistory},
        //使用者管理
        {path: '/adminManage', component: adminManage},
        //老师管理
        {path: '/teacherManage', component: teacherManage},
        //宝宝管理
        {path: '/babyManage', component: babyManage},
        //家长管理
        {path: '/patriarchManage', component: patriarchManage},
        //年级、班级 管理
        {path: '/gradeAndClassManage', component: gradeAndClassManage},
        //毕业管理
        {path: '/graduateManage', component: graduateManage},
        //升班管理
        {path: '/upGrade', component: upGrade},
        //考勤时间管理
        {path: '/attendanceTimeManage', component: attendanceTimeManage},
        //考勤卡管理
        {path: '/attendanceCardManage', component: attendanceCardManage},
        //考勤异常
        {path: '/attendanceAnomalyManage', component: attendanceAnomalyManage},
        //请假管理
        {path: '/leaveManage', component: leaveManage},
        //课程管理
        {path: '/curriculumManage', component: curriculumManage},
        //晨检查看
        {path: '/morningCheckLook', component: morningCheckLook},
        //付费查询
        {path: '/payQuery', component: payQuery},
        //食谱设置
        {path: '/cookbookSet', component: cookbookSet},
        //食谱列表
        {path: '/cookbookList', component: cookbookList},
        //全园缴费
        {path: '/payFees',component: payFees}

    ];
    var router = new VueRouter({
        routes: routes
    });
    var main = new Vue({
        router: router,
        el: '#main'
    });
})(window, document);
