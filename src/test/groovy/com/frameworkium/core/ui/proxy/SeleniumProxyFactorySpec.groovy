package com.frameworkium.core.ui.proxy

import org.openqa.selenium.Proxy
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class SeleniumProxyFactorySpec extends Specification {

    def "providing a property of (#proxyProp) gives proxy of type (#proxyType)"() {
        when:
            def proxy = SeleniumProxyFactory.createProxy(proxyProp)
        then:
            proxy.proxyType == proxyType
        where:
            proxyProp          | proxyType
            "system"           | Proxy.ProxyType.SYSTEM
            "autodetect"       | Proxy.ProxyType.AUTODETECT
            "direct"           | Proxy.ProxyType.DIRECT
            "http://host:1234" | Proxy.ProxyType.MANUAL
    }

    def "providing a proxy URL (#proxyProp) is set as expected (#proxyUrl)"() {
        when:
            def proxy = SeleniumProxyFactory.createManualProxy(proxyProp)
        then:
            proxy.proxyType == Proxy.ProxyType.MANUAL
            proxy.httpProxy == proxyUrl
            proxy.ftpProxy == proxyUrl
            proxy.sslProxy == proxyUrl
        where:
            proxyProp         | proxyUrl
            "http://host:123" | "host:123"
            "ftp://host:123"  | "host:123"
    }

    def "invalid proxy URL (#invalidProxyProp) throw exception"() {
        when:
            SeleniumProxyFactory.getProxyURL(invalidProxyProp)
        then:
            thrown(IllegalArgumentException)
        where:
            invalidProxyProp << ["host:123", "http://host", "", null]
    }
}
