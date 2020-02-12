	function displayit(n) {

		for (i = 0; i < 4; i++) {

			if (i == n) {

				var id = 'menu_list' + n;

				if (document.getElementById(id).style.display == 'none') {

					document.getElementById(id).style.display = '';

					document.getElementById("plug-wrap").style.display = '';

				} else {

					document.getElementById(id).style.display = 'none';

					document.getElementById("plug-wrap").style.display = 'none';

				}

			} else {

				if ($('#menu_list' + i)) {

					$('#menu_list' + i).css('display', 'none');

				}

			}

		}

	}

	function closeall() {

		var count = document.getElementById("doc").getElementsByTagName("ul").length;

		for (i = 0; i < count; i++) {

			document.getElementById("doc").getElementsByTagName("ul").item(i).style.display = 'none';

		}

		document.getElementById("plug-wrap").style.display = 'none';

	}

	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {

		WeixinJSBridge.call('hideToolbar');

	});