DEFAULTTUNE ?= "ppc970"

require conf/machine/include/powerpc/arch-powerpc64.inc

TUNEVALID[ppc970] = "Enable ppc970 specific processor optimizations"
TUNE_CCARGS .= "${@bb.utils.contains('TUNE_FEATURES', 'ppc970', ' -mcpu=970 -mno-spe ', '', d)}"

AVAILTUNES += "ppc970"
TUNE_FEATURES_tune-ppc970 = "m64 fpu-hard ppc970 altivec"
TUNE_PKGARCH_tune-ppc970 = "ppc970"
PACKAGE_EXTRA_ARCHS_tune-ppc970 = "${PACKAGE_EXTRA_ARCHS_tune-powerpc64} ppc970"

# glibc configure options to get 970 specific library (for sqrt)
GLIBC_EXTRA_OECONF += "${@bb.utils.contains('TUNE_FEATURES', 'ppc970', '--with-cpu=power4', '', d)}"
