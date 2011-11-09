set terminal epslatex leveldefault color blacktext \
   dashed dashlength 1.0 linewidth 2.0 butt \
   palfuncparam 2000,0.003 \
   noheader "" 10 
   
set output 'density.tex'

set yrange [0:2]
set xrange [0:500]

set size 0.8

set ylabel 'Density'
set xlabel 'Time [s]'

set title 'Population Density vs. Time'

plot './outputs/MeanHareDensities' u 1:2  w l lw 3 title 'Hares', './outputs/MeanPumaDensities' u 1:2 w l lw 3 title 'Pumas'

