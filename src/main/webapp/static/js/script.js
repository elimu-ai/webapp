

//pie graph 

// based on prepared DOM, initialize echarts instance
var pieapps = echarts.init(document.getElementById('chartapps'));
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
  

pieapps.setOption(option); 


//line chart for student performance

// based on prepared DOM, initialize echarts instance
var chartlinestudent = echarts.init(document.getElementById('chart-line-student'));

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
chartlinestudent.setOption(option); 



// based on prepared DOM, initialize echarts instance
var chartbarstudentloc = echarts.init(document.getElementById('chart-bar-student'));
var option = {
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['Kenya','Tanzania']
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
            data : ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'Kenya',
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
        },
        {
            name:'Tanzania',
            type:'bar',
            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
            markPoint : {
                data : [
                    {name : 'Max', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                    {name : 'Min', value : 2.3, xAxis: 11, yAxis: 3}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : 'average'}
                ]
            }
        }
    ]
};


chartbarstudentloc.setOption(option); 
