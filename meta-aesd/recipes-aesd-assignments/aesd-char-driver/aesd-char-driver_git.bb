LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-renkh.git;protocol=ssh;branch=master \
           file://aesdchar-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "230a63b5bd6707de4b80e020dde06adbc4a3477c"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop"

FILES:${PN} += "\
    ${bindir}/aesdchar_load \
    ${bindir}/aesdchar_unload \
    ${sysconfdir}/init.d/aesdchar-start-stop \
"

do_configure () {
    :
}

do_compile () {
    oe_runmake
}

do_install  () {
    install -m 0755 -d ${D}/${bindir}
    install -m 0755 -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 -d ${D}/${sysconfdir}/init.d

    install -m 0755 ${S}/aesdchar_load ${D}${bindir}
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${WORKDIR}/aesdchar-start-stop ${D}${sysconfdir}/init.d
}
