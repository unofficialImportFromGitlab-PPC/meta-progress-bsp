# linux-yocto-custom.bb:
#
#   An example kernel recipe that uses the linux-yocto and oe-core
#   kernel classes to apply a subset of yocto kernel management to git
#   managed kernel repositories.
#
#   To use linux-yocto-custom in your layer, copy this recipe (optionally
#   rename it as well) and modify it appropriately for your machine. i.e.:
#
#     COMPATIBLE_MACHINE_yourmachine = "yourmachine"
#
#   You must also provide a Linux kernel configuration. The most direct
#   method is to copy your .config to files/defconfig in your layer,
#   in the same directory as the copy (and rename) of this recipe and
#   add file://defconfig to your SRC_URI.
#
#   To use the yocto kernel tooling to generate a BSP configuration
#   using modular configuration fragments, see the yocto-bsp and
#   yocto-kernel tools documentation.
#
# Warning:
#
#   Building this example without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   defconfig: When a defconfig is provided, the linux-yocto configuration
#              uses the filename as a trigger to use a 'allnoconfig' baseline
#              before merging the defconfig into the build. 
#
#              If the defconfig file was created with make_savedefconfig, 
#              not all options are specified, and should be restored with their
#              defaults, not set to 'n'. To properly expand a defconfig like
#              this, specify: KCONFIG_MODE="--alldefconfig" in the kernel
#              recipe.
#   
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition (for kernel v4.x only):
#            SRC_URI += "file://0001-linux-version-tweak.patch"
#   example feature addition (for kernel v4.x only):
#            SRC_URI += "file://feature.scc"
#

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${KBRANCH};name=machine \
	    file://fix_strncpy_related_build_failures_with_GCC_8_1.patch\
	    "

SRC_URI_append_minig4 = "\
	    file://minig4/minig4-standard.scc \
	    file://minig4/defconfig.cfg \
            file://minig4/pmac32.cfg \
            file://minig4/minig4-user-config.cfg \
            file://minig4/minig4-user-features.scc \
            file://graphics.cfg \
            file://console-debug.cfg \
           "       

SRC_URI_append_powermacg5 = "\
            file://powermacg5/powermacg5-standard.scc\
            file://powermacg5/defconfig.cfg \
            file://powermacg5/powermacg5-user-config.cfg \
            file://powermacg5/powermacg5-user-features.scc \
            file://graphics.cfg \
            file://console-debug.cfg \
           "  
            
KBRANCH = "linux-4.14.y"     


LINUX_VERSION ?= "4.14"
LINUX_VERSION_EXTENSION_append = "-custom"

# Modify SRCREV to a different commit hash in a copy of this recipe to
# build a different release of the Linux kernel.
# tag: v4.2 64291f7db5bd8150a74ad2036f1037e6a0428df2
SRCREV="${AUTOREV}"
PR = "r0"

PV = "${LINUX_VERSION}.33"

PR := "${PR}.6"

COMPATIBLE_MACHINE_minig4 = "minig4"
#KERNEL_FEATURES_append_minig4 += " cfg/smp.scc"

COMPATIBLE_MACHINE_powermacg5 = "powermacg5"
#KERNEL_FEATURES_append_powermacg5 += " cfg/smp.scc"


do_shared_workdir () {
	cd ${B}

	kerneldir=${STAGING_KERNEL_BUILDDIR}
	install -d $kerneldir

	#
	# Store the kernel version in sysroots for module-base.bbclass
	#

	echo "${KERNEL_VERSION}" > $kerneldir/${KERNEL_PACKAGE_NAME}-abiversion

	# Copy files required for module builds
	cp System.map $kerneldir/System.map-${KERNEL_VERSION}
	cp Module.symvers $kerneldir/
	cp .config $kerneldir/
	mkdir -p $kerneldir/include/config
	cp include/config/kernel.release $kerneldir/include/config/kernel.release
	if [ -e certs/signing_key.pem ]; then
		# The signing_key.* files are stored in the certs/ dir in
		# newer Linux kernels
		mkdir -p $kerneldir/certs
		cp certs/signing_key.* $kerneldir/certs/
	elif [ -e signing_key.priv ]; then
		cp signing_key.* $kerneldir/
	fi

	# We can also copy over all the generated files and avoid special cases
	# like version.h, but we've opted to keep this small until file creep starts
	# to happen
	if [ -e include/linux/version.h ]; then
		mkdir -p $kerneldir/include/linux
		cp include/linux/version.h $kerneldir/include/linux/version.h
	fi

	# As of Linux kernel version 3.0.1, the clean target removes
	# arch/powerpc/lib/crtsavres.o which is present in
	# KBUILD_LDFLAGS_MODULE, making it required to build external modules.
	if [ ${ARCH} = "powerpc" ] && [ -e arch/powerpc/lib/crtsavres.o ]; then
		mkdir -p $kerneldir/arch/powerpc/lib/
		cp arch/powerpc/lib/crtsavres.o $kerneldir/arch/powerpc/lib/crtsavres.o
	fi

	if [ -d include/generated ]; then
		mkdir -p $kerneldir/include/generated/
		cp -fR include/generated/* $kerneldir/include/generated/
	fi

	if [ -d arch/${ARCH}/include/generated ]; then
		mkdir -p $kerneldir/arch/${ARCH}/include/generated/
		cp -fR arch/${ARCH}/include/generated/* $kerneldir/arch/${ARCH}/include/generated/
	fi
}
