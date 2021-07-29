uniform vec4 diffuseColor;
uniform int hasTexture;
uniform float incandescence;
uniform vec2 textureScale;
uniform vec2 textureOffset;
uniform int opacityState;
uniform int multiplyAlpha;
uniform float alphaTestThreshold;

varying vec2 texCoord;
varying vec4 lightViewportTexCoordDivW;
varying vec3 worldPosition;
varying vec3 worldNormal;
varying float viewDepth;

#pragma include PTLight.inc.fsh

void main() {
    vec4 color;
    if (hasTexture == 1) {
        color = texture2D(CC_Texture0, texCoord / textureScale + textureOffset) * diffuseColor;
        
        if (color.a <= alphaTestThreshold) {
            discard;
        }
    }
    else {
        color = diffuseColor;
    }
    
    float shadow = 1.0;
    computeShadow(worldNormal, viewDepth, lightViewportTexCoordDivW, shadow);

	float inc = mix(0.5, 0.0, incandescence);
    vec4 shade = vec4(shadow, shadow, shadow, 1.0);
    
    gl_FragColor = color * mix(vec4(1.0, 1.0, 1.0, 0.0), shade, vec4(inc, inc, inc, 1.0));
    
    if (opacityState == 0) {
        gl_FragColor.a = 1.0;
    }
    else if (diffuseColor.a < 1.0 && multiplyAlpha == 1) {
        gl_FragColor.r *= diffuseColor.a;
        gl_FragColor.g *= diffuseColor.a;
        gl_FragColor.b *= diffuseColor.a;
    }
}
