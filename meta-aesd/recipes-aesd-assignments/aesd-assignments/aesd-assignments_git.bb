# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit update-rc.d

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-renkh.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "f4a4e11bc4a99decd989b1ddb915b4386d057467"

S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket"
TARGET_LDFLAGS += "-pthread -lrt"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -m 0755 -d ${D}/${bindir}
	install -m 0755 -d ${D}/${sysconfdir}/init.d

    install -m 0755 ${S}/aesdsocket ${D}${bindir}
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d
}
