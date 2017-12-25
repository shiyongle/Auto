Ext.define("Ext.app.MyDateUtility", {
			singleton : true,
			alias : "MyDateUtility",

			getNowFormatDate : function() {
				
				return this.getNowFormatDateInGivenSeparator("-");
			},
			getNowFormatDateInGivenSeparator : function (separator) {
			
				var day = new Date();
				var Year = 0;
				var Month = 0;
				var Day = 0;
				var CurrentDate = "";
				// 初始化时间
				// Year= day.getYear();//有火狐下2008年显示108的bug
				Year = day.getFullYear();// ie火狐下都可以
				Month = day.getMonth() + 1;
				Day = day.getDate();
				// Hour = day.getHours();
				// Minute = day.getMinutes();
				// Second = day.getSeconds();
				CurrentDate += Year + separator;
				if (Month >= 10) {
					CurrentDate += Month + separator;
				} else {
					CurrentDate += "0" + Month + separator;
				}
				if (Day >= 10) {
					CurrentDate += Day;
				} else {
					CurrentDate += "0" + Day;
				}
				return CurrentDate;
				
			}

		});