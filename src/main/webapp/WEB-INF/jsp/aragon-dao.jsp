<content:title>
    <fmt:message key="aragon.dao" />
</content:title>

<content:section cssId="aragonDaoPage">
    <h2><content:gettitle /></h2>

    <div class="card-panel">
        <div id="aragonDaoContainer" style="text-align: center; margin: 5em;">
            <div class="preloader-wrapper big active">
                <div class="spinner-layer spinner-blue-only">
                  <div class="circle-clipper left">
                    <div class="circle"></div>
                  </div><div class="gap-patch">
                    <div class="circle"></div>
                  </div><div class="circle-clipper right">
                    <div class="circle"></div>
                  </div>
                </div>
            </div>
            <p>
                Loading DAO information...
            </p>
        </div>
        <script>
            $(function() {
                const url = 'http://localhost:3000/';
                console.info('Loading data from ' + url);
                $('#aragonDaoContainer').load(url, function() {
                    console.info('#aragonDaoContainer load complete');
                    
                });
            });
        </script>
    </div>
</content:section>

<content:aside>
    <h5 class="center">Decentralized Autonomous Organization (DAO)</h5>
    <p>
        The <a href="https://mainnet.aragon.org/#/elimuai" target="_blank">elimu.ai Community DAO</a> is empowered by <a href="https://aragon.org" target="_blank">Aragon</a>. 
        Aragon gives internet communities the power to freely organize and collaborate without borders or intermediaries.
    </p>
    <p>
        For more information, see our blog post: <a href="https://medium.com/elimu-ai/why-did-the-elimu-ai-community-decide-to-use-aragon-e9863c135111" target="_blank">Why Did the elimu.ai Community Decide to Use Aragon? 🦅</a>.
    </p>
    <a href="https://mainnet.aragon.org/#/elimuai">
        <img src="https://wiki.aragon.org/design/artwork/Powered_By/SVG/Powered_By_White.svg" alt="Powered by Aragon" />
    </a>
</content:aside>
