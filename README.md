Maven
=====
UniformGenerator implements the RandomVectorGenerator to get uniform number
NormalGenerator implements the RandomVectorGenerator to get Gaussian. Put uniform random vector to the best device that kernel finds, applies the Box-Muller transformation to form a normal random vector.

Result:
European: 6.2143, simulation times 4943724;
Asian:1.98303, simulation times 847955;
If you want to get Asian price, just change the European to Asian in the SimulationManager Class.



BINHAOZHOU
bz548@nyu.edu
