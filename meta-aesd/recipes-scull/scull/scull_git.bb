# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-renkh;protocol=https;branch=master \
           file://0001-Include-misc-modules-and-scull.patch \
           file://insmod-scull-path.patch \
           file://S98lddmodules \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "28fa8cd99cefc965dda540b53fc5eaf643e8935c"

S = "${WORKDIR}/git"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/scull"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules"

FILES:${PN} += "\
    ${bindir}/scull_load \
    ${bindir}/scull_unload \
    ${sysconfdir}/* \
"

do_configure () {
    :
}

do_compile () {
    oe_runmake
}

do_install () {
    install -m 0755 -d ${D}${bindir}
    install -m 0755 -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 -d ${D}${sysconfdir}/init.d

    install -m 0755 ${S}/scull/scull_load ${D}${bindir}
    install -m 0755 ${S}/scull/scull_unload ${D}${bindir}
    install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/init.d
}
