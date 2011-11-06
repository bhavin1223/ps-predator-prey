set terminal epslatex leveldefault color blacktext \
   dashed dashlength 1.0 linewidth 2.0 butt \
   palfuncparam 2000,0.003 \
   noheader "" 10 

set output 'overhead.tex'

set xrange [0:6]
set yrange [-3.5:0]

set size 1

set ylabel 'log$_{10}$ Time/Cell [s]'
set xlabel 'log$_{10}$ Number of Cells'

set title 'Run Time/Number of Cells vs. Number of Cells'


plot './timing.dat' u (log10($3)):(log10($2/$3)) smooth csplines title 'Run Time/Number of Cells' lw 3, './timing.dat' u (log10($3)):(log10($2/$3)) title '' with points 3 3

