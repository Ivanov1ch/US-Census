var mapData = null;
var isShowingPopulation = true;

$("path, circle").hover(function (e) {
    $('#info-box').css('display', 'block');
    $('#info-box').html($(this).attr('data-info'));
    $('#info-box').css('border-color', $(this).attr(isShowingPopulation ? 'data-pop-color' : 'data-growth-color'));
});

$("path, circle").mouseleave(function (e) {
    $('#info-box').css('display', 'none');
});

$(document).mousemove(function (e) {
    $('#info-box').css('top', e.pageY - $('#info-box').height() - 30);
    $('#info-box').css('left', e.pageX - ($('#info-box').width()) / 2);

    var top = parseFloat($('#info-box').css('top').slice(0, -1));
    var left = parseFloat($('#info-box').css('left').slice(0, -1));

    // Off-screen correction
    if (top < 0)
        $('#info-box').css('top', e.pageY + 30);
    if (left < 0)
        $('#info-box').css('top', e.pageX + 30);

}).mouseover();

var ios = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
if (ios) {
    $('a').on('click touchend', function () {
        var link = $(this).attr('href');
        window.open(link, '_blank');
        return false;
    });
}

// Taken from https://stackoverflow.com/questions/2901102/how-to-print-a-number-with-commas-as-thousands-separators-in-javascript
function formatNumber(number) {
    var splitNum;
    number = Math.abs(number);
    number = number.toFixed(2);
    splitNum = number.split('.');
    splitNum[0] = splitNum[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return splitNum.join(".");
}

function getNewData(currentData, stateData) {
    var retString = currentData;
    retString += '<hr><div>Predicted Population for ' + mapData.year + ': <br />' + formatNumber(stateData.population) + ' (#' + stateData.population_rank + ' in the US)</div>';
    retString += '<hr><div>Predicted Population Growth/Shrink Rate: <br />' + formatNumber(stateData.rate_of_growth) + ' people per year (#' + stateData.fastest_growth_rank + ' in the US)</div>'
    return retString;
}

// From https://www.sitepoint.com/javascript-generate-lighter-darker-color/
function lightenDarkenColor(hex, lum) {
    // validate hex string
    hex = String(hex).replace(/[^0-9a-f]/gi, '');
    if (hex.length < 6) {
        hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2];
    }
    lum = lum || 0;

    // convert to decimal and change luminosity
    var rgb = "#", c, i;
    for (i = 0; i < 3; i++) {
        c = parseInt(hex.substr(i * 2, 2), 16);
        c = Math.round(Math.min(Math.max(0, c + (c * lum)), 255)).toString(16);
        rgb += ("00" + c).substr(c.length);
    }

    return rgb;
}

function toggleDisplay() {
    isShowingPopulation = !isShowingPopulation;
    $("path, circle").each(function (indx, element) {
        var elem = $(element);
        if (elem.hasClass('ignore'))
            return; // Ignore the two paths that don't belong

        elem.css('fill', elem.attr(isShowingPopulation ? 'data-pop-color' : 'data-growth-color'));
    });
}

$(document).ready(function () {
    $.getJSON("static/map-data.json", function (json) {
        mapData = json;
    }).then(function () {
        $("path, circle").each(function (indx, element) {
            var elem = $(element);
            if (elem.hasClass('ignore'))
                return; // Ignore the two paths that don't belong

            var stateData = mapData[elem.attr('id')];
            var currentData = elem.attr('data-info');

            elem.attr('data-info', getNewData(currentData, stateData));
            elem.attr('data-pop-color', mapData.colors[parseInt(stateData.population_rank) - 1]);
            elem.attr('data-pop-hover-color', lightenDarkenColor(elem.attr('data-pop-color'), -0.25));
            elem.attr('data-growth-color', mapData.colors[parseInt(stateData.fastest_growth_rank) - 1]);
            elem.attr('data-growth-hover-color', lightenDarkenColor(elem.attr('data-growth-color'), -0.25));

            elem.css('fill', elem.attr('data-pop-color'));

            elem.mouseenter(function (e) {
                elem.css('stroke', elem.attr(isShowingPopulation ? 'data-pop-hover-color' : 'data-growth-hover-color'));
                elem.css('fill', elem.attr(isShowingPopulation ? 'data-pop-hover-color' : 'data-growth-hover-color'));
            });
            elem.mouseleave(function (e) {
                elem.css('stroke', 'unset');
                elem.css('fill', elem.attr(isShowingPopulation ? 'data-pop-color' : 'data-growth-color'));
            });
        });

        $('#loading-screen').css('opacity', 0);
        setTimeout(function () {
            $('#loading-screen').css('visibility', 'hidden')
        }, 1000);
    });
});

var footer = $('footer:first');

function adjustFooter() {
    if ($('header:first').outerHeight() + $('main:first').outerHeight() + footer.outerHeight() < $(window).height()) {
        footer.css({
            'position': 'fixed',
            'left': 0,
            'right': 0,
            'bottom': 0
        });
    } else {
        footer.css({
            'position': 'static',
            'left': 'unset',
            'right': 'unset',
            'bottom': 'unset'
        });
    }
}

$(window).bind('resize', function () {
    adjustFooter();
});
$(adjustFooter());