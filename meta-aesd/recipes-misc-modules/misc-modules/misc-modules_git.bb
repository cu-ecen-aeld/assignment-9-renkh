LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-renkh;protocol=https;branch=master \
           file://0001-Include-misc-modules-and-scull.patch \
           file://insmod-misc-modules-path.patch \
           file://misc-modules-load-unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "28fa8cd99cefc965dda540b53fc5eaf643e8935c"

S = "${WORKDIR}/git"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/misc-modules"

FILES:${PN} += "\
    ${bindir}/module_load \
    ${bindir}/module_unload \
    ${sysconfdir}/* \
"

do_configure () {
    :
}

do_compile () {
    oe_runmake
}

do_install () {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 -d ${D}/${sysconfdir}/init.d

    install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}
    install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}
    install -m 0755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${S}/misc-modules/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${WORKDIR}/misc-modules-load-unload ${D}${sysconfdir}/init.d
}
