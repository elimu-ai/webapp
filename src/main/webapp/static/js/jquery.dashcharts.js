//INIT CHARTS
var licensing_chart
	require.config({
	    paths: {
	    	echarts: '/literacyapp-webapp/static/js'
	    }
	});
	require(
	    [
	        'echarts',
	        'echarts/chart/line',   // load-on-demand, don't forget the Magic switch type.
	        'echarts/chart/bar',
	        'echarts/chart/pie',
	        'echarts/chart/gauge'
	    ],
	    function (ec) {
	        //licensing_chart = ec.init(document.getElementById('licensing_status')).setOption(licensing);
	       
	    }
	);
	function chartsResize(){
        //licensing_chart.resize();
	}
	window.onresize = chartsResize;