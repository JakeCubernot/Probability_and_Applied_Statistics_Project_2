%{
  This script was made within the editor of Octave

  Sources:
  https://docs.octave.org/latest/Comments.html
  https://docs.octave.org/v4.0.0/Two_002dDimensional-Plots.html
  https://octave.sourceforge.io/octave/function/size.html
  https://www.mathworks.com/help/matlab/ref/movmean.html
  https://www.mathworks.com/help/matlab/ref/legend.html
%}

x = -25:0.1:25;
y = sin(x / 2);

saltedFunctionY = y + ((rand(size(y)) * 6) - 3);

smoothedFunctionY = movmean(saltedFunctionY, 46);

plot(x, saltedFunctionY, 'g', x, y, 'b', x, smoothedFunctionY, 'r');
legend('Salted Function', 'Original Function', 'Smoothed Function');

