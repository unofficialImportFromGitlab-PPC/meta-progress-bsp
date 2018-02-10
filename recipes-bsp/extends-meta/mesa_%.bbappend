DRIDRIVERS_append_powerpc = ",radeon,r200,nouveau"
DRIDRIVERS_append_powerpc64 = ",radeon,r200,nouveau"
GALLIUMDRIVERS_append_powerpc = "${@bb.utils.contains('PACKAGECONFIG', 'gallium-llvm', ',${GALLIUMDRIVERS_LLVM}', '', d)}"
GALLIUMDRIVERS_append_powerpc64 = "${@bb.utils.contains('PACKAGECONFIG', 'gallium-llvm', ',${GALLIUMDRIVERS_LLVM}', '', d)}"
 
