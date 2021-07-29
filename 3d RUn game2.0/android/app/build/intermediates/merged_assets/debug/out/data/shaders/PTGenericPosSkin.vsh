const int SKINNING_JOINT_COUNT = 60;

uniform mat4 worldToLightViewportTexCoord;
uniform vec4 u_matrixPalette[SKINNING_JOINT_COUNT * 3];

attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord;
attribute vec4 a_blendWeight;
attribute vec4 a_blendIndex;

varying vec2 texCoord;
varying vec4 lightViewportTexCoordDivW;
varying vec3 worldPosition;
varying vec3 worldNormal;
varying float viewDepth;

void getPositionAndNormal(out vec4 position, out vec3 normal) {
    float blendWeight = a_blendWeight[0];

    int matrixIndex = int(a_blendIndex[0]) * 3;
    vec4 matrixPalette1 = u_matrixPalette[matrixIndex] * blendWeight;
    vec4 matrixPalette2 = u_matrixPalette[matrixIndex + 1] * blendWeight;
    vec4 matrixPalette3 = u_matrixPalette[matrixIndex + 2] * blendWeight;

    blendWeight = a_blendWeight[1];
    
    if (blendWeight > 0.0) {
        matrixIndex = int(a_blendIndex[1]) * 3;
        matrixPalette1 += u_matrixPalette[matrixIndex] * blendWeight;
        matrixPalette2 += u_matrixPalette[matrixIndex + 1] * blendWeight;
        matrixPalette3 += u_matrixPalette[matrixIndex + 2] * blendWeight;

        blendWeight = a_blendWeight[2];
        
        if (blendWeight > 0.0) {
            matrixIndex = int(a_blendIndex[2]) * 3;
            matrixPalette1 += u_matrixPalette[matrixIndex] * blendWeight;
            matrixPalette2 += u_matrixPalette[matrixIndex + 1] * blendWeight;
            matrixPalette3 += u_matrixPalette[matrixIndex + 2] * blendWeight;

            blendWeight = a_blendWeight[3];
            
            if (blendWeight > 0.0) {
                matrixIndex = int(a_blendIndex[3]) * 3;
                matrixPalette1 += u_matrixPalette[matrixIndex] * blendWeight;
                matrixPalette2 += u_matrixPalette[matrixIndex + 1] * blendWeight;
                matrixPalette3 += u_matrixPalette[matrixIndex + 2] * blendWeight;
            }
        }
    }

    position.x = dot(a_position, matrixPalette1);
    position.y = dot(a_position, matrixPalette2);
    position.z = dot(a_position, matrixPalette3);
    position.w = a_position.w;

    vec4 n = vec4(a_normal, 0.0);
    normal.x = dot(n, matrixPalette1);
    normal.y = dot(n, matrixPalette2);
    normal.z = dot(n, matrixPalette3);
}

void main() {
    vec4 position;
    vec3 normal;
    getPositionAndNormal(position, normal);
    
    texCoord = a_texCoord;
    texCoord.y = 1.0 - texCoord.y;

    worldPosition = (CC_MVMatrix * position).xyz;
    worldNormal = (CC_NormalMatrix * normal).xyz;
    lightViewportTexCoordDivW = worldToLightViewportTexCoord * vec4(worldPosition, 1.0);
	
    gl_Position = CC_MVPMatrix * position;
    viewDepth = gl_Position.z;
}
