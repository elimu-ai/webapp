<content:title>
    Donate 💜
</content:title>

<content:section cssId="appealPage">
    <h2><content:gettitle /></h2>  
    
    <img src="https://user-images.githubusercontent.com/15718174/82723985-51250780-9d05-11ea-8fc6-e800d9b414eb.png" 
         alt="Children interacting with an educational Android app" style="border-radius: 8px;" />
    <blockquote style="border-left-color: #dfe2e5; color: #6a737d;">
        A collection of <a href="<spring:url value='/' />" target="_blank">educational Android apps</a> teaches children 
        basic literacy and numeracy in a fun and engaging way.
    </blockquote>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <p>
        In order to achieve our purpose of providing disadvantaged children with access to quality basic education, the 
        elimu.ai Community focuses on three <a href="https://github.com/elimu-ai/wiki/projects" target="_blank">main activities</a>:
    </p>
    <div class="chip deep-purple lighten-2 white-text">1. Content</div>
    <div class="chip light-green darken-2 white-text">2. Engineering</div>
    <div class="chip cyan darken-1 white-text">3. Distribution</div>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <a name="donate-cryptocurrency"></a>
    <h3>Donate Cryptocurrency</h3>
    <div class="card-panel">
        <p>
            To donate, use this Ethereum address:
        </p>
        <p>
            <svg style="width: 24px; height: 24px; top: 6px; position: relative; right: 5px;" viewBox="0 0 784.37 1277.39" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xodm="http://www.corel.com/coreldraw/odm/2003">
                <g>
                    <polygon fill="#343434" fill-rule="nonzero" points="392.07,0 383.5,29.11 383.5,873.74 392.07,882.29 784.13,650.54 "/>
                    <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,0 -0,650.54 392.07,882.29 392.07,472.33 "/>
                    <polygon fill="#3C3C3B" fill-rule="nonzero" points="392.07,956.52 387.24,962.41 387.24,1263.28 392.07,1277.38 784.37,724.89 "/>
                    <polygon fill="#8C8C8C" fill-rule="nonzero" points="392.07,1277.38 392.07,956.52 -0,724.89 "/>
                    <polygon fill="#141414" fill-rule="nonzero" points="392.07,882.29 784.13,650.54 392.07,472.33 "/>
                    <polygon fill="#393939" fill-rule="nonzero" points="0,650.54 392.07,882.29 392.07,472.33 "/>
                </g>
            </svg><code>0x883753Beab357A2c29f3766C6ad158e72A78ce51</code>
        </p>
        <p>
            Thank you! 💜
        </p>
    </div>
    
    <h4>Most Recent Donations</h4>
    <div class="card-panel">
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.etherscan.io/address/0x883753Beab357A2c29f3766C6ad158e72A78ce51" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://etherscan.io/address/0x883753Beab357A2c29f3766C6ad158e72A78ce51" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h3>Allocation of Donated Funds</h3>
    <div id="allocationChartLoadingContainer">
        <div class="progress">
            <div class="indeterminate"></div>
        </div>
        <p>
            Loading...
        </p>
    </div>
    <div id="allocationChartContainer" style="display: none;">
        <canvas id="chart-area"></canvas>
    </div>
    <script>
        function displayPieChart(
                sumContent,
                sumEngineering,
                sumDistribution
        ) {
            console.info('displayPieChart');
            
            var chartColors = {
                cyan: 'rgb(0,172,193)',
                green: 'rgb(104,159,56)',
                purple: 'rgb(149,117,205)'
                
            };

            var config = {
                type: 'pie',
                data: {
                    datasets: [{
                        data: [
                            sumContent,
                            sumEngineering,
                            sumDistribution
                        ],
                        backgroundColor: [
                            chartColors.purple,
                            chartColors.green,
                            chartColors.cyan
                        ],
                        label: 'Allocation of Donated Funds'
                    }],
                    labels: [
                        'Content (33%)',
                        'Engineering (33%)',
                        'Distribution (33%)'
                    ]
                },
                options: {
                    responsive: true
                }
            };
            
            var context = document.getElementById('chart-area').getContext('2d');
            window.myPie = new Chart(context, config);
        }
        
        $(function() {
            $('#allocationChartLoadingContainer').remove();
            $('#allocationChartContainer').show();
            displayPieChart(0.33, 0.33, 0.33);
        });
    </script>
    
    <p>
        When you donate, you can tell us where you would like us to allocate your funds. You can also tell us 
        <a href="https://github.com/elimu-ai/wiki#milestones" target="_blank">which language</a> you want us to prioritize when 
        we add more educational content.
    </p>
    
    <h4>Most Recent Payouts</h4>
    <div class="card-panel">
        <h5>#content 🎶🎙</h5>
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.etherscan.io/address/0xa406C9B6c115A65da97EC7256d0A1fF51F281f71" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://etherscan.io/address/0xa406C9B6c115A65da97EC7256d0A1fF51F281f71" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
        
        <h5>#engineering 👩🏽‍💻📱</h5>
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.etherscan.io/address/0xB1C409722B23Cba26dE8660b180d4B55Ed8CB4c0" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://etherscan.io/address/0xB1C409722B23Cba26dE8660b180d4B55Ed8CB4c0" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
        
        <h5>#distribution 🛵💨</h5>
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.etherscan.io/address/0x282EF5377C35E90C33a94833d69dBA880430c9cB" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://etherscan.io/address/0x282EF5377C35E90C33a94833d69dBA880430c9cB" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Global Learning Crisis 🌍</h5>
    <div class="card-panel deep-purple lighten-5">
        <p>
            Worldwide, 64 million children of primary school age (about 6 to 11 years) are still out of school.
        </p>

        <p>
            The number of children who are excluded from education fell steadily in the decade following 2000, but 
            <a href="http://uis.unesco.org/en/news/new-education-data-sdg-4-and-more" target="_blank">UNESCO statistics</a> show 
            that this progress essentially stopped in recent years:
        </p>

        <script src="<spring:url value='/static/js/chart.bundle.min-2.8.0.js' />"></script>
        <link rel="stylesheet" href="<spring:url value='/static/css/chart.min-2.8.0.css' />" />
        <canvas id="myChart" width="400" height="200"></canvas>
        <script>
            var ctx = document.getElementById('myChart');

            var data = {
                labels: ['2000', '2001', '2002', '2003', '2004', '2005', '2006', '2007', '2008', '2009', '2010', '2011', '2012', '2013', '2014', '2015', '2016', '2017'],
                datasets: [{
                    label: '# of out-of-school children',
                    data: [100.895204, 96.939208, 89.807926, 81.040811, 75.683716, 74.265254, 71.741023, 63.755341, 62.325118, 63.171650, 62.268521, 62.201916, 60.735142, 61.810741, 62.334523, 62.436744, 62.241641, 63.670048],
                    backgroundColor: 'rgba(103,58,183, 0.2)',
                    borderColor: 'rgba(103,58,183, 0.9)',
                    borderWidth: 3
                }]
            };

            var myLineChart = new Chart(ctx, {
                type: 'line',
                data: data,
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            });
        </script>
        
        <div class="divider" style="margin-bottom: 1em;"></div>
        
        <div class="center">
            <a href="#donate-cryptocurrency" id="donateNowButton" class="btn waves-effect waves-light deep-purple lighten-2">Donate now</a>
            <script>
                $(function() {
                  $('#donateNowButton').click(function(event) {
                      event.preventDefault();
                      $('html, body').animate({
                          scrollTop: $('[name="donate-cryptocurrency"]').offset().top
                      }, 1000);
                  });
                });
            </script>
        </div>
    </div>
</content:aside>
