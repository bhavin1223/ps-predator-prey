set terminal epslatex leveldefault color blacktext \
   dashed dashlength 1.0 linewidth 2.0 butt \
   palfuncparam 2000,0.003 \
   noheader "" 10 
   
set output 'buffering.tex'

set xrange[0:1000]
set yrange[0.2:1.4]

set size 1

set xlabel 'Output Frequency (Timesteps)'
set ylabel 'log$_{10}$ Run Time [s]'

set title 'Effect of Buffered File Writing'

plot './output_timing100.dat' u 1:(log10($2)) smooth bezier lw 3 title '100x100 Buffered', './output_timing100.dat' u 1:(log10($3)) smooth bezier lw 3 title '100x100 Unbuffered'

