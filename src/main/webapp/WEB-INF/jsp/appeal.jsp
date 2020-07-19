<content:title>
    Appeal
</content:title>

<content:section cssId="appealPage">
    <h2><content:gettitle /></h2>
    
    <h4>Global Learning Crisis 🌍</h4>
    
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
                data: [100895204, 96939208, 89807926, 81040811, 75683716, 74265254, 71741023, 63755341, 62325118, 63171650, 62268521, 62201916, 60735142, 61810741, 62334523, 62436744, 62241641, 63670048],
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
    
    <p>&nbsp;</p>
    
    
    <h4>How Can We Solve the Problem? 💡</h4>    
    
    <p>
        Our solution is to develop software for teaching out-of-school children the basics of reading, writing and arithmetic. 
        A collection of <a href="<spring:url value='/' />" target="_blank">educational apps</a> teaches children basic literacy and numeracy in a fun and engaging way. 
        This empowers each child to learn fully autonomously, without depending on the availability of qualified teachers.
    </p>
    
    <div class="row">
        <div class="col s10">
            <img src="https://elimu-ai.atlassian.net/wiki/download/thumbnails/163842/global_learning_xprize.jpg?version=1&modificationDate=1520688110913&cacheVersion=1&api=v2&width=738&height=415" />
        </div>
        <div class="col s2">
            <img src="https://github.blog/wp-content/uploads/2019/05/xprize-no-text.png?w=1200" /><br />
            <img src="https://user-images.githubusercontent.com/15718174/27299402-95bea44c-552c-11e7-84ab-217cdca758e4.gif" /><br />
            <img src="https://user-images.githubusercontent.com/15718174/27515871-41872c60-59ae-11e7-9b2d-3ca886d0d7f2.png" /><br />
            <img src="<spring:url value='/static/img/publish-literacy.png' />" /><br />
            <img src="<spring:url value='/static/img/publish-literacy2.png' />" />
        </div>
    </div>
    
    <%-- TODO: include information about guiding principles --%>
    
    <p>&nbsp;</p> 
        
    
    <h4>Would You Like to Contribute?</h4>
    
    <p>
        The purpose of the <i>elimu.ai Community</i> is to provide access to quality basic education to out-of-school children. We 
        deeply believe that all children have the right to a quality basic education no matter their social or geographic background.
    </p>
    
    <p>
        Please help us make this a reality by making a contribution. Donated funds will be directed towards software development and distribution. 
        Thank you! 💜
    </p>
    
    <p>&nbsp;</p>
    
    
    <h4>Donate Money</h4>
    
    <div class="row">
        <div class="col s5">
            <ul>
                <li>
                    <b>Step 1/3</b><br />
                    Select which language you would like to support.
                </li>
                <li>
                    <i class="material-icons">arrow_downward</i>
                </li>
                <li>
                    <b>Step 2/3</b><br />
                    Select a donation amount and transfer via payment card or your bank.
                </li>
                <li>
                    <i class="material-icons">arrow_downward</i>
                </li>
                <li>
                    <b>Step 3/3</b><br />
                    Leave your contact information so that we can follow up and share the impact of your donation.
                </li>
            </ul>
            
            <p>&nbsp;</p>
            
            <div class="divider"></div> 
            
            <p class="grey-text">
                <b>Note:</b>
            </p>
            <ol class="browser-default" style="list-style-type: inherit;">
                <li class="grey-text">100% of your donation will be spent on efforts that actually result in a child getting access to quality 
                basic education.</li>
                <li class="grey-text">0% of your donation will be spent on administrative overhead costs.</li>
            </ol>
        </div>
        <div class="col offset-s1 s6 z-depth-1 white center" style="border-radius: 8px;">
            <div style="padding: 1em;">
                <h2><i class="material-icons large deep-purple-text">card_giftcard</i></h2>

                <p>
                  Step 1/3
                </p>
                <div class="progress">
                    <div class="determinate" style="width: 33%"></div>
                </div>  
                <p>
                  We create educational content matching a child's <i>mother tongue</i>. Which language would you like your donation to support?
                </p>
                
                <form action="<spring:url value='/appeal' />" method="POST">
                    <div class="row left-align">
                        <div class="input-field col s12">
                            <input name="donationLanguage" id="donationLanguageAny" type="radio" checked />
                            <label for="donationLanguageAny">Any language (let us decide for you)</label>
                        </div>
                        <%--<div class="input-field col s12">
                            <input name="donationLanguage" id="donationLanguageENGLISH" value="ENGLISH" type="radio" />
                            <label for="donationLanguageENGLISH">English</label>
                        </div>--%>
                        <div class="input-field col s12">
                            <input name="donationLanguage" id="donationLanguageHINDI" value="HINDI" type="radio" />
                            <label for="donationLanguageHINDI">Hindi (India)</label>
                        </div>
                        <div class="input-field col s12">
                            <input name="donationLanguage" id="donationLanguageSWAHILI" value="SWAHILI" type="radio" />
                            <label for="donationLanguageSWAHILI">Swahili (Tanzania, Kenya, Uganda)</label>
                        </div>
                    </div>
                    
                    <p>&nbsp;</p>

                    <button id="submitButton" class="btn-large waves-effect waves-light deep-purple" type="submit">
                        <fmt:message key="next" /><i class="material-icons right">arrow_forward</i>
                    </button>
                </form>
            </div>
        </div>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Primary Out-of-School Rate</h5>
    <p>
        <img src="https://user-images.githubusercontent.com/15718174/60810127-a65b3780-a18c-11e9-811c-dc0edb6f270a.png" />
    </p>
    
    <table class="striped">
        <thead>
          <tr>
              <th>Region</th>
              <th>Number</th>
              <th>Rate</th>
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>Sub-Saharan Africa</td>
            <td>34.5 million</td>
            <td>20.5%</td>    
          </tr>
          <tr>
            <td>Southern Asia</td>
            <td>11.6 million</td>
            <td>6.3%</td>
          </tr>
          <tr>
            <td>Eastern and South-Eastern Asia</td>
            <td>6.6 million</td>
            <td>3.8%</td>
          </tr>
          <tr>
            <td>Northern Africa and Western Asia</td>
            <td>5.7 million</td>
            <td>10.5%</td>
          </tr>
          <tr>
            <td>Latin America and the Caribbean</td>
            <td>2.8 million</td>
            <td>4.7%</td>
          </tr>
        </tbody>
      </table>
</content:aside>
