DRIDRIVERS_append_powerpc = ",radeon,r200,nouveau"
DRIDRIVERS_append_powerpc64 = ",radeon,r200,nouveau"
GALLIUMDRIVERS_append_powerpc = ",nouveau"
GALLIUMDRIVERS_append_powerpc64 = ",nouveau"

GALLIUMDRIVERS_append_powerpc = "${@bb.utils.contains('PACKAGECONFIG', 'gallium-llvm', ',${GALLIUMDRIVERS_LLVM}', '', d)}"
GALLIUMDRIVERS_append_powerpc64 = "${@bb.utils.contains('PACKAGECONFIG', 'gallium-llvm', ',${GALLIUMDRIVERS_LLVM}', '', d)}"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'wayland vulkan', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl egl gles gbm dri gallium', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'x11', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 vulkan', 'dri3', '', d)} \
                   "
 
