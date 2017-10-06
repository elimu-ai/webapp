

//pie graph 

// based on prepared DOM, initialize echarts instance
//var pieapps = echarts.init(document.getElementById('chartapps'));

var option = {
    title : {
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['org.literacyapp','com.android.gallery3d','com.cyanogenmod.eleven','org.literacyapp.voltair','org.literacyapp.calculator','org.literacyapp.walezi','org.literacyapp.handwriting','org.literacyapp.startguide','org.literacyapp.nya']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true, 
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        x: '25%',
                        width: '50%',
                        funnelAlign: 'left',
                        max: 1548
                    }
                }
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'org.literacyapp'},
                {value:310, name:'com.android.gallery3d'},
                {value:234, name:'com.cyanogenmod.eleven'},
                {value:135, name:'org.literacyapp.voltair'},
                {value:500, name:'org.literacyapp.calculator'},
                {value:200, name:'org.literacyapp.walezi'},
                {value:200, name:'org.literacyapp.handwriting'},
                {value:100, name:'org.literacyapp.startguide'},
                {value:548, name:'org.literacyapp.nya'}
            ]
        }
    ]
};
  

//pieapps.setOption(option); 


//line chart for student performance

// based on prepared DOM, initialize echarts instance
//var chartlinestudent = echarts.init(document.getElementById('chart-line-student'));

var option = {
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['student','device']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],
    series : [
        {
            name:'student',
            type:'line',
            data:[11, 11, 15, 13, 12, 13, 10],
            markPoint : {
                data : [
                    {type : 'max', name: 'max'},
                    {type : 'min', name: 'min'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: 'avg'}
                ]
            }
        },
        {
            name:'device',
            type:'line',
            data:[6, 5, 8, 9, 3, 5, 7],
            markPoint : {
                data : [
                    {type : 'max', name: 'max'},
                    {type : 'min', name: 'min'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : 'avg'}
                ]
            }
        }
    ]
};
//chartlinestudent.setOption(option); 




//application event open
 // 'JSON' data included as above
//data = '[{"blue" : "is ok", "red" : "is my fave color"}]';


//pie graph 

// based on prepared DOM, initialize echarts instance
var applicationopenevent = echarts.init(document.getElementById('application-open-event'));
//alert(applicationopenevent)
var option = {
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['nya','lighting','calculator','chat','literacyapp']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'nya',
            type:'line',
            stack: 'total',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'lighting',
            type:'line',
            stack: 'total',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'calculator',
            type:'line',
            stack: 'total',
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'chat',
            type:'line',
            stack: 'total',
            data:[220, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'literacyapp',
            type:'line',
            stack: 'total',
            data:[220, 332, 401, 434, 290, 430, 420]
        }
    ]
};    
applicationopenevent.setOption(option);



//device

// based on prepared DOM, initialize echarts instance
var chartbardevice = echarts.init(document.getElementById('chart-bar-device'));
var option = {
    tooltip : {
        trigger: 'axis'
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'device',
            type:'bar',
            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            markPoint : {
                data : [
                    {type : 'max', name: 'max'},
                    {type : 'min', name: 'min'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: 'average'}
                ]
            }
        }
    ]
};


chartbardevice.setOption(option); 



//student

// based on prepared DOM, initialize echarts instance
var chartbarstudent = echarts.init(document.getElementById('chart-bar-student'));

var option = {
    tooltip : {
        trigger: 'axis'
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'device',
            type:'bar',
            data:[4.0, 5, 7.0, 21, 23, 80, 135, 165, 34, 20.0, 6.4, 3.3],
            markPoint : {
                data : [
                    {type : 'max', name: 'max'},
                    {type : 'min', name: 'min'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: 'average'}
                ]
            }
        }
    ]
};


chartbarstudent.setOption(option); 

