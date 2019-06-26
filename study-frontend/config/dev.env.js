var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    // API_URL: '"http://192.168.2.44:8089"',
	//ACCESS_TOKEN: '"a9b17692-0d9c-4025-b993-4f10cfed73e0"'

	// API_URL: '"http://192.168.1.124:1030/fjs/"',
	// ACCESS_TOKEN: '"3340a3b2-c7b0-42f2-8edf-688e5dd589e7"',

    API_URL: '"http://47.97.165.203:8888"',
    ACCESS_TOKEN: '"a9b17692-0d9c-4025-b993-4f10cfed73e0"'

    // API_URL: '"https://api.fangjinyun.com.cn/fjs/pandora-backend"',ia
    // ACCESS_TOKEN:'"6797fec1-dbcd-4efb-82c2-ba43c12ceda8"'
})
