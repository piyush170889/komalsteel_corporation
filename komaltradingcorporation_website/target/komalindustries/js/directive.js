app.filter('range', function() {
  return function(input, total) {
    var value=total.toString();
  	var values=value.split('-');

      var minvalue = values[0];
      var maxvalue = values[1];
   // total = parseInt(total);
      var increment = scope.$eval(attrs.increment);
    for (var i=0; i<=maxvalue; i++) {
      input.push(i);
      i+increment;
    }

    return input;
  };
});

