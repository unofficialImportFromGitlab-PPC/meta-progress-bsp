FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#PR := "${PR}.2"

COMPATIBLE_MACHINE_minig4 = "minig4"
#KERNEL_FEATURES_append_minig4 += " cfg/smp.scc"

COMPATIBLE_MACHINE_powermacg5 = "powermacg5"
KERNEL_FEATURES_append_powermacg5 += " cfg/smp.scc"

SRC_URI += "\
            file://graphics.cfg \
            file://console-debug.cfg \
            "
SRC_URI_append_minig4 = "file://minig4/minig4-standard.scc \
            file://minig4/pmac32.cfg \
            file://minig4/minig4-user-config.cfg \
            file://minig4/minig4-user-features.scc \
           "
SRC_URI_append_powermacg5 = "\
            file://powermacg5/powermacg5-standard.scc \
            file://powermacg5/g5_defconfig.cfg \
            file://powermacg5/powermacg5-user-config.cfg \
            file://powermacg5/powermacg5-user-features.scc \
           "       


# replace these SRCREVs with the real commit ids once you've had
# the appropriate changes committed to the upstream linux-yocto repo
SRCREV_machine_pn-linux-yocto_minig4 ?= "${AUTOREV}"
SRCREV_meta_pn-linux-yocto_minig4 ?= "${AUTOREV}"

SRCREV_machine_pn-linux-yocto_powermacg5 ?= "${AUTOREV}"
SRCREV_meta_pn-linux-yocto_powermacg5 ?= "${AUTOREV}"

#SRCREV_machine ?= "1ae25422cbe108fbc706022fb5e22562913f6c56"

#LINUX_VERSION = "4.9.80"
#PV = "${LINUX_VERSION}+git${SRCPV}"
