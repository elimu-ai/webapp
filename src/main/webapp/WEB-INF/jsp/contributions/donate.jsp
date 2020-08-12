<content:title>
    Donate
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
        In order to achieve our purpose of providing out-of-school children with access to quality basic education, the 
        elimu.ai Community focuses on three <a href="https://github.com/elimu-ai/wiki/projects" target="_blank">main activities</a>:
    </p>
    <div class="chip deep-purple lighten-2 white-text">1. Content</div>
    <div class="chip light-green darken-2 white-text">2. Engineering</div>
    <div class="chip cyan darken-1 white-text">3. Distribution</div>
    
    
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
                sumContentBen,
                sumContentEng,
                sumContentFil,
                sumContentHin,
                sumContentSwa,
                sumContentUrd,
                sumEngineering,
                sumDistribution,
                sumUnallocated
        ) {
            console.info('displayPieChart');
            
            var chartColors = {
                cyan: 'rgb(0,172,193)',
                gray: 'rgb(176,190,197)',
                green: 'rgb(104,159,56)',
                purple: 'rgb(149,117,205)'
            };

            var config = {
                type: 'pie',
                data: {
                    datasets: [{
                        data: [
                            sumContent,
                            sumContentBen,
                            sumContentEng,
                            sumContentFil,
                            sumContentHin,
                            sumContentSwa,
                            sumContentUrd,
                            sumEngineering,
                            sumDistribution,
                            sumUnallocated
                        ],
                        backgroundColor: [
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.purple,
                            chartColors.green,
                            chartColors.cyan,
                            chartColors.gray
                        ],
                        label: 'Allocation of Donated Funds'
                    }],
                    labels: [
                        'Content (any language)',
                        'Content (Bengali)',
                        'Content (English)',
                        'Content (Filipino)',
                        'Content (Hindi)',
                        'Content (Swahili)',
                        'Content (Urdu)',
                        'Engineering',
                        'Distribution',
                        'Unallocated (no preference)'
                    ]
                },
                options: {
                    responsive: true
                }
            };
            
            var context = document.getElementById('chart-area').getContext('2d');
            window.myPie = new Chart(context, config);
        }
    </script>
    <p>
        When you donate, you can tell us where you would like us to allocate your funds. You can also tell us 
        <a href="https://github.com/elimu-ai/wiki#milestones" target="_blank">which language</a> you want us to prioritize when 
        we add more educational content.
    </p>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <h4>Most Recent Donations</h4>
    <div class="card-panel">
        <div id="donationsContainer">
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
            <p>
                Loading...
            </p>
        </div>
        <script>
            /**
             * Copied from AragonRestController.java
             */
            function getBaseUrl() {
                console.info("getBaseUrl");
                let domain = "62.75.236.14"; // DEV/TEST
                <c:if test="${applicationScope.configProperties['env'] == 'PROD'}">
                    domain = "85.93.91.26";
                </c:if>
                return "http://" + domain + ":3000";
            }
            
            /**
             * E.g. "2020-03-31 13:35"
             */
            function getFormattedDate(timestamp) {
                console.info("getFormattedDate");
                let date = new Date(timestamp * 1000);
                let year = date.getFullYear();
                let month = (date.getMonth() + 1).toString();
                if (month.length == 1) {
                    month = "0" + month;
                }
                let day = date.getDate().toString();
                if (day.length == 1) {
                    day = "0" + day;
                }
                let hour = date.getHours().toString();
                if (hour.length == 1) {
                    hour = "0" + hour;
                }
                let minute = date.getMinutes().toString();
                if (minute.length == 1) {
                    minute = "0" + minute;
                }
                return year + "-" + month + "-" + day + " " + hour + ":" + minute;
            }
            
            /**
             * E.g. "#content" --> "<span class="chip deep-purple lighten-2 white-text">#content</span>"
             */
            function getLabeledReference(reference) {
                console.info("getLabeledReference");
                if (reference.includes('#content') 
                        && !reference.includes('#content-')) {
                    reference = reference.replace('#content', '<span class="chip deep-purple lighten-2 white-text">#content</span>');
                } else if (reference.includes('#content-ben')) {
                    reference = reference.replace('#content-ben', '<span class="chip deep-purple lighten-2 white-text">#content-ben</span>');
                } else if (reference.includes('#content-eng')) {
                    reference = reference.replace('#content-eng', '<span class="chip deep-purple lighten-2 white-text">#content-eng</span>');
                } else if (reference.includes('#content-fil')) {
                    reference = reference.replace('#content-fil', '<span class="chip deep-purple lighten-2 white-text">#content-fil</span>');
                } else if (reference.includes('#content-hin')) {
                    reference = reference.replace('#content-hin', '<span class="chip deep-purple lighten-2 white-text">#content-hin</span>');
                } else if (reference.includes('#content-swa')) {
                    reference = reference.replace('#content-creatio-swan', '<span class="chip deep-purple lighten-2 white-text">#content-swa</span>');
                } else if (reference.includes('#content-urd')) {
                    reference = reference.replace('#content-urd', '<span class="chip deep-purple lighten-2 white-text">#content-urd</span>');
                } else if (reference.includes('#engineering')) {
                    reference = reference.replace('#engineering', '<span class="chip light-green darken-2 white-text">#engineering</span>');
                } else if (reference.includes('#distribution')) {
                    reference = reference.replace('#distribution', '<span class="chip cyan darken-1 white-text">#distribution</span>');
                }
                return reference;
            }

            $(function() {
                // Fetch finance transactions from Aragon Connect (via the REST API)
                $.ajax({
                    dataType: "json",
                    url: "<spring:url value='/rest/v2/aragon/finance-transactions' />",
                    success: function(financeTransactions) {
                        console.info("success");
                        
                        // Prepare values to be displayed in the pie-chart for allocation of funds
                        let sumContent = 0.00;
                        let sumContentBen = 0.00;
                        let sumContentEng = 0.00;
                        let sumContentFil = 0.00;
                        let sumContentHin = 0.00;
                        let sumContentSwa = 0.00;
                        let sumContentUrd = 0.00;
                        let sumEngineering = 0.00;
                        let sumDistribution = 0.00;
                        let sumUnallocated = 0.00;
                        
                        // Display newest transactions on top
                        financeTransactions.reverse();

                        let htmlString = '<table class="striped responsive-table">';
                        htmlString += '    <thead>';
                        htmlString += '        <tr>';
                        htmlString += '            <th>Source</th>';
                        htmlString += '            <th>Amount</th>';
                        htmlString += '            <th>Reference</th>';
                        htmlString += '        </tr>';
                        htmlString += '    </thead>';
                        htmlString += '    <tbody>';
                        financeTransactions.forEach(function(financeTransaction, index) {
                            // Exclude outgoing transactions
                            if (!financeTransaction.isIncoming) {
                                return;
                            }
                            
                            // TODO: add support for any type of token
                            // Exclude tokens that are not ETH
                            if (financeTransaction.token !== '0x0000000000000000000000000000000000000000') {
                                return;
                            }
                            
                            let ethAmount = Number((financeTransaction.amount/1000000000000000000).toFixed(5));
                            
                            // Summarize funds per activity
                            if (financeTransaction.reference.includes('#content') 
                                    && !financeTransaction.reference.includes('#content-')) {
                                sumContent += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-ben')) {
                                sumContentBen += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-eng')) {
                                sumContentEng += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-fil')) {
                                sumContentFil += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-hin')) {
                                sumContentHin += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-swa')) {
                                sumContentSwa += ethAmount;
                            } else if (financeTransaction.reference.includes('#content-urd')) {
                                sumContentUrd += ethAmount;
                            } else if (financeTransaction.reference.includes('#engineering')) {
                                sumEngineering += ethAmount;
                            } else if (financeTransaction.reference.includes('#distribution')) {
                                sumDistribution += ethAmount;
                            } else {
                                sumUnallocated += ethAmount;
                            }
                            
                            htmlString += '<tr>';
                            htmlString += '    <td>';
                            htmlString += '        <div class="chip">';
                            htmlString += '            <img src="' + getBaseUrl() +'/identicon/' + financeTransaction.entity + '" />' + financeTransaction.entity.substring(0, 6) + "..." + financeTransaction.entity.substring(financeTransaction.entity.length - 4, financeTransaction.entity.length);
                            htmlString += '        </div><br />';
//                            htmlString += '        ' + getFormattedDate(financeTransaction.date);
                            htmlString += '    </td>';
                            htmlString += '    <td>';
                            htmlString += '        ' + ethAmount.toFixed(5) + ' ETH';
                            htmlString += '    </td>';
                            htmlString += '    <td>';
                            htmlString += '        ' + getLabeledReference(financeTransaction.reference);
                            htmlString += '    </td>';                

                            htmlString += '</tr>';
                        });
                        htmlString += '    </tbody>';
                        htmlString += '</table>';
                        $('#donationsContainer').html(htmlString);
                        
                        // Display in the pie-chart for allocation of funds
                        $('#allocationChartLoadingContainer').remove();
                        $('#allocationChartContainer').show();
                        displayPieChart(
                                sumContent,
                                sumContentBen,
                                sumContentEng,
                                sumContentFil,
                                sumContentHin,
                                sumContentSwa,
                                sumContentUrd,
                                sumEngineering,
                                sumDistribution,
                                sumUnallocated
                        );
                    }
                });
            });
        </script>
        
        <div class="divider" style="margin: 1em 0;"></div>
        
        <c:choose>
            <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                <a href="https://rinkeby.aragon.org/#/elimuai/0x7a2711f547696fff3fc1788b9295c5464e4a7edd/" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:when>
            <c:otherwise>
                <a href="https://mainnet.aragon.org/#/elimuai/0x25e71ca07476c2a65c289c7c6bd6910079e119e6/" target="_blank">
                    View all transactions <i class="material-icons">launch</i>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
    
    <div class="divider" style="margin: 1.5em 0;"></div>
    
    <a name="donate-cryptocurrency"></a>
    <h3>Donate Cryptocurrency</h3>
    <div class="card-panel">
        <p>
            To donate, follow these steps:
        </p>
        <ol>
            <li>
                Make sure to <a href="https://www.youtube.com/watch?v=sO3WVSFd5ng" target="_blank">enable Web3</a> in your browser
            </li>
            <li>
                Go to the elimu.ai Community's 
                <c:choose>
                    <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                        <a href="https://rinkeby.aragon.org/#/elimuai/0x7a2711f547696fff3fc1788b9295c5464e4a7edd/" target="_blank">Finance App</a> 
                    </c:when>
                    <c:otherwise>
                        <a href="https://mainnet.aragon.org/#/elimuai/0x25e71ca07476c2a65c289c7c6bd6910079e119e6/" target="_blank">Finance App</a> 
                    </c:otherwise>
                </c:choose>
                and press "Connect account"
            </li>
            <li>
                Then press "New transfer"
            </li>
            <li>
                Let us know where to allocate your funds by typing any of these values into the "Reference" field: 
                <ul>
                    <li>
                        <span class="chip deep-purple lighten-2 white-text">#content</span>
                        <span class="chip light-green darken-2 white-text">#engineering</span>
                        <span class="chip cyan darken-1 white-text">#distribution</span>
                    </li>
                </ul>
            </li>
            <li>
                Finally, press "Submit deposit". Then sign the transaction, and verify that your donation appears in the list above.
            </li>
            <li>
                Thank you! 😀
            </li>
        </ol>
        
        <div class="divider" style="margin: 1.5em 0;"></div>
        
        <div class="center">
            <c:choose>
                <c:when test="${applicationScope.configProperties['env'] != 'PROD'}">
                    <a href="https://rinkeby.aragon.org/#/elimuai/0x7a2711f547696fff3fc1788b9295c5464e4a7edd/"
                            target="_blank"
                            class="btn-large waves-effect waves-light deep-purple lighten-2">
                        Donate now
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="https://mainnet.aragon.org/#/elimuai/0x25e71ca07476c2a65c289c7c6bd6910079e119e6/"
                            target="_blank"
                            class="btn-large waves-effect waves-light deep-purple lighten-2">
                        Donate now
                    </a>
                </c:otherwise>
            </c:choose><br />
            <br />
            <a href="https://aragon.org" target="_blank">
                <img src="https://wiki.aragon.org/design/artwork/Powered_By/SVG/Powered_By_White.svg" 
                     alt="Powered by Aragon" height="64px" />
            </a>
        </div>
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
