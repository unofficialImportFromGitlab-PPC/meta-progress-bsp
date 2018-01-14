FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.2"

COMPATIBLE_MACHINE_minig4 = "minig4"



#KERNEL_FEATURES_append_minig4 += " cfg/smp.scc"

SRC_URI += "file://minig4-standard.scc \
            file://pmac32.cfg \
            file://minig4-fragment.cfg \
            file://minig4-graphics.cfg \
            file://console-debug.cfg \
            file://minig4-user-features.scc \
           "

# replace these SRCREVs with the real commit ids once you've had
# the appropriate changes committed to the upstream linux-yocto repo
SRCREV_machine_pn-linux-yocto_minig4 ?= "${AUTOREV}"
SRCREV_meta_pn-linux-yocto_minig4 ?= "${AUTOREV}"

LINUX_VERSION = "4.8.26"
